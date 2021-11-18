package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Picture (

	val large : String? = null,
	val medium : String? = null,
	val thumbnail : String? = null
)