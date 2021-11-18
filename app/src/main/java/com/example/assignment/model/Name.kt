package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Name (

	val title : String? = null,
	val first : String? = null,
	val last : String? = null
)