package io.buildwithnd.demotmdb.util

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import com.example.assignment.model.Error

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
}