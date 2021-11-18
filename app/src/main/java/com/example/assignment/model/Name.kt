package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Name (

	val title : String,
	val first : String,
	val last : String
)