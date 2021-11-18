package com.example.assignment.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.ProfileRepository
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.ProfileDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import com.example.assignment.model.Result
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: ProfileRepository) :
        ViewModel() {

    private val _profileList = MutableLiveData<Result<MatchProfileResponse>>()
    val profileList = _profileList

    private val _updateProfileItem = MutableLiveData<Result<Boolean>>()
    val updateProfileItem = _updateProfileItem


    fun fetchProfiles() {
        viewModelScope.launch {
            movieRepository.fetchProfiles(10).collect {
                _profileList.value = it
            }
        }
    }

    fun updateProfile(profileDetails: ProfileDetails) {
        viewModelScope.launch {
            movieRepository.updateProfile(profileDetails).collect {
                _updateProfileItem.value = it
            }
        }
    }
}