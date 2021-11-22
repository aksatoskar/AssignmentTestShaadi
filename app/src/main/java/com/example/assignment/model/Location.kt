package com.example.assignment.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Location (
	val city : String? = null,
	val state : String? = null,
	val country : String? = null,
	val postcode : String? = null,
)