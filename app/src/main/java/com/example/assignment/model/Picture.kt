package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Picture (

	val large : String,
	val medium : String,
	val thumbnail : String
)