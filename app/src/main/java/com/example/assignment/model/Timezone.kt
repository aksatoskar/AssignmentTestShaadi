package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Timezone (

	val offset : String? = null,
	val description : String? = null
)