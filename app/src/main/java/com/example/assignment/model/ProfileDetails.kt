package com.example.assignment.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.model.Id


@Entity
data class ProfileDetails (
	val gender : String? = null,
	@Embedded
	val name : Name? = null,
	@Embedded
	val location : Location? = null,
	val email : String? = null,
	@NonNull
	@Embedded
	@PrimaryKey
	val login : Login,
	@Embedded
	val dob : Dob? = null,
	val phone : String? = null,
	val cell : String? = null,
	@Embedded
	val id : Id? = null,
	@Embedded
	val picture : Picture? = null,
	val nat : String? = null,
	var action: Int? = null
)