package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Dob (

	val date : String? = null,
	val age : Int? = null
)