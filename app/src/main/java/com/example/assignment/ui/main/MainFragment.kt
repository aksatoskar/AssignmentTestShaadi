package com.example.assignment.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import appendList
import com.example.assignment.databinding.MainFragmentBinding
import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Result

import dagger.hilt.android.AndroidEntryPoint
import io.buildwithnd.demotmdb.util.AppConstants.ACCEPTED
import io.buildwithnd.demotmdb.util.AppConstants.PROFILE_ACCEPTED
import io.buildwithnd.demotmdb.util.AppConstants.PROFILE_DECLINED

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchProfiles()
        subscribeUi()
        binding.rvProfiles.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ProfilesAdapter { items: MutableList<ProfileDetails>, changed: ProfileDetails, action: String ->

            var element = items.first { it.login.uuid == changed.login.uuid }
            val index = items.indexOf(element)

            element = if(action == ACCEPTED) {
                element.copy(action = PROFILE_ACCEPTED)
            } else {
                element.copy(action = PROFILE_DECLINED)
            }
            viewModel.updateProfile(element)

            items[index] = element

            (binding.rvProfiles.adapter as ProfilesAdapter).submitList(items)

        }

        binding.rvProfiles.adapter = adapter

    }



    private fun subscribeUi() {
        viewModel.profileList.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        (binding.rvProfiles.adapter as ProfilesAdapter).appendList(list)
                    }
                    _binding?.loading?.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        //showError(it)
                    }
                    _binding?.loading?.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    _binding?.loading?.visibility = View.VISIBLE
                }
            }

        })

        viewModel.updateProfileItem.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data.let { isUpdatedSuccessfully->

                    }
                    _binding?.loading?.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        //showError(it)
                    }
                    _binding?.loading?.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    _binding?.loading?.visibility = View.VISIBLE
                }
            }

        })
    }
}