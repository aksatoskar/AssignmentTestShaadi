package com.example.assignment.data.local

import androidx.room.*
import com.example.assignment.model.ProfileDetails

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiledetails")
    fun getAll(): List<ProfileDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(profiles: List<ProfileDetails>)

    @Update
    fun update(profileDetails: ProfileDetails)
}