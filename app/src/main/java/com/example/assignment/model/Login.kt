package com.example.assignment.model

import androidx.room.Entity

@Entity
data class Login (
	val uuid : String,
	val username : String
)