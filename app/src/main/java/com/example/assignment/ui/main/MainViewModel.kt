package com.example.assignment.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.ProfileRepository
import com.example.assignment.model.MatchProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import com.example.assignment.model.Result
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * ViewModel for ListingActivity
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: ProfileRepository) :
        ViewModel() {

    private val _movieList = MutableLiveData<Result<MatchProfileResponse>>()
    val movieList = _movieList


    fun fetchProfiles() {
        viewModelScope.launch {
            movieRepository.fetchProfiles(10).collect {
                _movieList.value = it
            }
        }
    }
}