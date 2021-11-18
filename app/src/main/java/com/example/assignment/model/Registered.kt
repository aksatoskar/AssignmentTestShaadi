package com.example.assignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Registered (

	@ColumnInfo(name = "registeredDate")
	val date : String? = null,
	@ColumnInfo(name = "registeredAge")
	val age : Int? = null
)