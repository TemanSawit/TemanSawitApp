package com.example.temansawit.ui.screen.profile

import BottomSheet
import BottomSheetType
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.data.Result
import com.example.temansawit.di.Preferences
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.Alert403
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.ui.theme.Green700
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    var name by remember { mutableStateOf("Temansawit Guest") }
    var email by remember { mutableStateOf("user@temansawit.com") }
    var imageUser by remember { mutableStateOf("https://www.citypng.com/public/uploads/preview/free-round-flat-male-portrait-avatar-user-icon-png-11639648873oplfof4loj.png") }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedBottomSheet by remember { mutableStateOf(BottomSheetType.None) }

    BottomSheet(
        modalSheetState = modalSheetState,
        selectedBottomSheet = selectedBottomSheet,
        onBottomSheetSelected = { sheetType ->
            selectedBottomSheet = sheetType
            coroutineScope.launch {
                modalSheetState.show()
            }
        }
    ) {
        ScaffoldApp(
            topBar = {
                AppBar(title = "Profile")
            },
            bottomBar = { BottomBar(navHostController = navHostController) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                if (currentRoute != Screen.DetailTransaction.route) {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            selectedBottomSheet = BottomSheetType.Camera
                            coroutineScope.launch {
                                modalSheetState.show()
                            }
                        },
                        backgroundColor = Green700,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_camera_alt_24),
                            contentDescription = "Deteksi Sawit"
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFC0DBCE))
                        .padding(horizontal = 16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        viewModel.name.collectAsState(initial = UiState.Loading).value.let { user ->
                            when (user) {
                                is UiState.Loading -> {
                                    viewModel.getUserProfile()
                                }
                                is UiState.Success -> {
                                        name = user.data.username.toString()
                                        email = user.data.email.toString()
                                        if (user.data.image != null ) {
                                            imageUser = user.data.image.toString()
                                        }
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = imageUser,
                                                builder = {
                                                    transformations(CircleCropTransformation())
                                                }
                                            ),
                                            contentDescription = "Profile Image",
                                            modifier = Modifier.size(60.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Hai",
                                                color = Color.Gray,
                                                fontSize = 15.sp
                                            )
                                            Text(
                                                text = name,
                                                color = Color.Gray,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = email,
                                                color = Color.DarkGray,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(top = 7.dp)
                                            )
                                        }
                                    }
                                }
                                is UiState.Error -> {
                                    Alert403(navHostController = navHostController)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    ProfileCard()
                    Spacer(modifier = Modifier.height(10.dp))
                    LogoutButton(navHostController = navHostController)
                }
            }
        )
    }
}

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Composable
fun ProfileCard() {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Edit Profil",
                    modifier = Modifier
                        .size(24.dp)

                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Edit Profil"),
                    onClick = {
                        val intent = Intent(context, EditProfileActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_reset),
                    contentDescription = "Ganti Password",
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Ganti Password"),
                    onClick = {
                        val intent = Intent(context, ChangePasswordActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_policy),
                    contentDescription = "Kebijakan Privasi",
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Kebijakan Privasi"),
                    onClick = {
                        val intent = Intent(context, PrivasiPolicyActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_description),
                    contentDescription = "Syarat dan Ketentuan",
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Syarat dan Ketentuan"),
                    onClick = {
                        val intent = Intent(context, TermsandConditionsActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_headset),
                    contentDescription = "Hubungi Kami",
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Hubungi Kami"),
                    onClick = {
                        val intent = Intent(context, ContactUsActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline),
                    contentDescription = "Tentang Kami",
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ClickableText(
                    text = AnnotatedString("Tentang Kami"),

                    onClick = {
                        val intent = Intent(context, AboutUsActivty::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}


@Composable
fun LogoutButton(
    navHostController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        val showDialog = remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { showDialog.value = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout_24),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Keluar")
            if (showDialog.value) {
                AlertLogout(navHostController = navHostController)
            }
        }
    }
}

@Composable
fun AlertLogout(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    Column() {
        val openDialog = remember { mutableStateOf(true) }
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Keluar dari akun")
            },
            text = {
                Text("Apakah anda yakin ingin keluar?")
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        viewModel.logoutUser().observe(lifecycleOwner, {
                            when (it) {
                                is Result.Loading -> {
                                    // Handle loading state if needed
                                }
                                is Result.Success -> {
                                    val sharedPreferences =
                                        context.getSharedPreferences(
                                            "my_preferences",
                                            Context.MODE_PRIVATE
                                        )
                                    Preferences.logoutUser(sharedPreferences)
                                    navHostController.popBackStack()
                                    navHostController.navigate("loginScreen") {
                                        popUpTo(navHostController.graph.findStartDestination().id)
                                    }
                                }
                                is Result.Error -> {
                                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                                }
                                else -> {}
                            }
                        })
                    }) {
                    Text("Konfirmasi Keluar")
                }
            },
            dismissButton = {
                Button(onClick = { openDialog.value = false }) {
                    Text(text = "Batal")
                }
            }
        )
    }
}