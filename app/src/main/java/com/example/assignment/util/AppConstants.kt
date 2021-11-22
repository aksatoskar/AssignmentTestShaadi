package com.example.assignment.util

import com.example.assignment.BuildConfig

/**
 * parses error response body
 */
object AppConstants {

    val LOCATION = "%s, %s"
    val ACCEPTED = "PROFILE_ACCEPTED"
    val DECLINED = "PROFILE_DECLINED"
    val PROFILE_ACCEPTED = 1
    val PROFILE_DECLINED = 0

    val ARG_ACTION = "ARG_ACTION"
    val BASE_URL = BuildConfig.API_BASE_URL
}