package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Registered (

	val date : String,
	val age : Int
)