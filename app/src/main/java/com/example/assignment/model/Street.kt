package com.example.assignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Street (

	val number : Int? = null,
	@ColumnInfo(name = "streetName")
	val name : String? = null
)