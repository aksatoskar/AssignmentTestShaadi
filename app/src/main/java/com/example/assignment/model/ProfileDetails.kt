package com.example.assignment.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.model.Id


@Entity
data class ProfileDetails (
	@NonNull
	@Embedded
	@PrimaryKey
	val id : Id,
	val gender : String,

	val email : String,

	val phone : String,
	val cell : String,

	val nat : String




)