package com.example.assignment.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import appendList
import com.example.assignment.databinding.MainFragmentBinding
import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Resource
import com.example.assignment.ui.main.viewmodel.MainViewModel
import com.example.assignment.ui.main.ProfilesAdapter
import com.example.assignment.util.hide
import com.example.assignment.util.show
import dagger.hilt.android.AndroidEntryPoint
import com.example.assignment.util.AppConstants.ACCEPTED
import com.example.assignment.util.AppConstants.PROFILE_ACCEPTED
import com.example.assignment.util.AppConstants.PROFILE_DECLINED
import kotlinx.coroutines.flow.collect

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchProfiles()
        subscribeUi()

        binding.btRetry.setOnClickListener {
            viewModel.fetchProfiles()
        }

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
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        resource.data?.results?.let { list ->
                            (binding.rvProfiles.adapter as ProfilesAdapter).appendList(list)
                        }
                        binding.loading.hide()
                    }
                    Resource.Status.LOADING -> {
                        binding.loading.show()
                        binding.llError.hide()
                    }
                    Resource.Status.ERROR -> {
                        binding.loading.hide()
                        if (resource.data?.results.isNullOrEmpty()) {
                            binding.llError.show()
                            binding.tvErrorMsg.text = resource.throwable?.message ?: ""
                        } else {
                            (binding.rvProfiles.adapter as ProfilesAdapter).submitList(resource.data?.results)
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updateProfileItem.collect { result ->
                if(result.status == Resource.Status.LOADING) {
                    _binding?.loading?.show()
                } else {
                    _binding?.loading?.hide()
                }
            }
        }
    }
}