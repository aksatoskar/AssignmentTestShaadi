package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Coordinates (

	val latitude : Double? = null,
	val longitude : Double? = null
)