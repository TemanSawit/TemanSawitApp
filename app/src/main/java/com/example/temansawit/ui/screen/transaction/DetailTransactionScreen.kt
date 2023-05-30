package com.example.temansawit.ui.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.temansawit.di.Injection
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.transaction.CardDetail
import com.example.temansawit.ui.components.transaction.CardNoTransaksi
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.R

@Composable
fun DetailTrxScreen(
    trxId: Long,
    viewmodel: TransactiomViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
    ) {
    viewmodel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewmodel.getTrxById(trxId)
            }
            is UiState.Success -> {
                val data = uiState.data
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                            },
                            navigationIcon = {
                                IconButton(onClick =  navigateBack) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_navigate_before_24),
                                        contentDescription = "Kembali",
                                        Modifier.size(32.dp)
                                    )
                                }
                            },
                            backgroundColor = GreenPressed,
                            contentColor = Color.White,
                            elevation = 10.dp
                        )
                    }
                ) { it
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(GreenPrimary.copy(alpha = 0.2F))
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .background(
                                        GreenPressed,
                                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                                    )
                            ) {
                                CardNoTransaksi()
                            }
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            ) {
                                CardDetail(
                                    berat = data.cardTransaction.berat,
                                    hargaPerKg = data.cardTransaction.hargaPerKg,
                                    total = data.cardTransaction.total,
                                    tanggal = data.cardTransaction.tanggal,
                                    deskripsi = data.cardTransaction.deskripsi,
                                    tint = data.cardTransaction.tint
                                )
                            }
                        }
                    }
                }

            }
            is UiState.Error -> {}
        }
    }
}

//class DetailTransactionActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            TemanSawitTheme {
//                // A surface container using the 'background' color from the theme
//                DetailTransactionApp()
//            }
//        }
//    }
//}


//@Composable
//fun DetailTransactionApp() {

//}