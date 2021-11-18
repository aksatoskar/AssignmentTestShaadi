package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Street (

	val number : Int,
	val name : String
)