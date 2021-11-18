package com.example.assignment.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Location (
	@Embedded
	val street : Street,
	val city : String,
	val state : String,
	val country : String,
	val postcode : Int,
	@Embedded
	val coordinates : Coordinates,
	@Embedded
	val timezone : Timezone
)