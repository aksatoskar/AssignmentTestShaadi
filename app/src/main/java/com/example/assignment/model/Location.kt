package com.example.assignment.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Location (
	@Embedded
	val street : Street? = null,
	val city : String? = null,
	val state : String? = null,
	val country : String? = null,
	val postcode : String? = null,
	@Embedded
	val coordinates : Coordinates? = null,
	@Embedded
	val timezone : Timezone? = null
)