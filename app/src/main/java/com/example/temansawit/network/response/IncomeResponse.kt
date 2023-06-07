package com.example.temansawit.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class IncomeResponse(
	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("IncomeResponse")
	val incomeResponse: List<IncomeResponseItem>
)

@Entity(tableName = "income")
data class IncomeResponseItem(
	@PrimaryKey
	@field:SerializedName("incomeId")
	val incomeId: Int,

	@field:SerializedName("transaction_time")
	val transactionTime: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("total_weight")
	val totalWeight: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("userId")
	val userId: Int,
)