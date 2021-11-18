package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Timezone (

	val offset : String,
	val description : String
)