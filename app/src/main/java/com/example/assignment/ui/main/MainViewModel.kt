package com.example.assignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.ProfileDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import com.example.assignment.model.Resource
import com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository.ISourceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var sourceRepository: ISourceRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<Resource<MatchProfileResponse>>(Resource.loading())
    val stateFlow: StateFlow<Resource<MatchProfileResponse>>
        get() = _stateFlow


    fun fetchProfiles() {
        viewModelScope.launch {
            sourceRepository.fetchProfiles(10).collect {
                _stateFlow.value = it
            }
        }
    }

    fun updateProfile(profileDetails: ProfileDetails) {
        viewModelScope.launch {
            sourceRepository.updateProfile(profileDetails)
        }
    }
}