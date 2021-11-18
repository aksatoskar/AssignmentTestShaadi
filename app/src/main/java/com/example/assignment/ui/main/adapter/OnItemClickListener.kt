package com.example.assignment.ui.main.adapter

import com.example.assignment.model.ProfileDetails
import java.text.FieldPosition

interface OnItemClickListener {
    fun onItemClick(position: Int, action: String, profileDetails: ProfileDetails)
}