package com.example.assignment.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.assignment.R
import com.example.assignment.databinding.ListItemProfileBinding
import com.example.assignment.model.ProfileDetails
import com.example.assignment.util.AppConstants
import com.example.assignment.util.AppConstants.ARG_ACTION
import com.example.assignment.util.AppConstants.LOCATION
import com.example.assignment.util.AppConstants.PROFILE_ACCEPTED
import com.example.assignment.util.AppConstants.PROFILE_DECLINED
import com.example.assignment.util.hide
import com.example.assignment.util.invisible
import com.example.assignment.util.show
import java.util.*

class ProfilesAdapter(val action: (items: MutableList<ProfileDetails>, changed: ProfileDetails, action: String) -> Unit):
    ListAdapter<ProfileDetails, ProfilesAdapter.ProfileViewHolder>(DiffCallback()) {

    private lateinit var binding: ListItemProfileBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        binding = ListItemProfileBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilesAdapter.ProfileViewHolder, position: Int) {
        onBindViewHolder(holder, position, Collections.emptyList())
    }

    override fun onBindViewHolder(holder: ProfilesAdapter.ProfileViewHolder, position: Int, payload: List<Any>) {
        val item = getItem(position)

        if (payload.isEmpty() || payload[0] !is Bundle) {
            holder.bind(item)
        } else {
            val bundle = payload[0] as Bundle
            holder.update(bundle)
        }

        holder.bind(getItem(position))
    }

    inner class ProfileViewHolder(private val binding: ListItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profileDetails: ProfileDetails) = with(itemView) {
            binding.tvName.text = "${profileDetails.name?.title} ${profileDetails.name?.first} ${profileDetails.name?.last}"
            profileDetails.dob?.age?.let { age->
                binding.tvAge.visibility = View.VISIBLE
                binding.tvAge.text = binding.root.context.getString(R.string.age, age)
            }

            profileDetails.location?.let { location->
                binding.tvLocation.text = String.format(LOCATION, location.city, location.country)
            }

            profileDetails.picture?.large?.let { url->
                binding.ivPoster.load(url)
            }

            updateUserActionUI(profileDetails)

            binding.btAccept.setOnClickListener {
                action(currentList.toMutableList(), profileDetails, AppConstants.ACCEPTED)
            }

            binding.btDecline.setOnClickListener {
                action(currentList.toMutableList(), profileDetails, AppConstants.DECLINED)
            }
        }

        private fun updateUserActionUI(profileDetails: ProfileDetails) {
            when(profileDetails.action) {
                PROFILE_ACCEPTED -> showUserActionUI(true)
                PROFILE_DECLINED -> showUserActionUI(false)
                else -> {
                    binding.tvAction.hide()
                    binding.btDecline.show()
                    binding.btAccept.show()
                }
            }
        }

        private fun showUserActionUI(isAccepted: Boolean) {
            binding.tvAction.show()
            binding.btDecline.invisible()
            binding.btAccept.hide()
            if(isAccepted) {
                binding.tvAction.text = binding.root.context.getString(R.string.profile_accepted)
                binding.tvAction.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            } else {
                binding.tvAction.text = binding.root.context.getString(R.string.profile_declined)
                binding.tvAction.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_400))
            }
        }

        fun update(bundle: Bundle) {
            if (bundle.containsKey(ARG_ACTION)) {
                val action = bundle.getInt(ARG_ACTION)
                when(action) {
                    PROFILE_ACCEPTED -> showUserActionUI(true)
                    PROFILE_DECLINED -> showUserActionUI(false)
                    else -> {
                        binding.tvAction.visibility = View.GONE
                        binding.btDecline.visibility = View.INVISIBLE
                        binding.btAccept.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<ProfileDetails>() {
    override fun areItemsTheSame(oldItem: ProfileDetails, newItem: ProfileDetails): Boolean {
        return oldItem.login.uuid == newItem.login.uuid
    }

    override fun areContentsTheSame(oldItem: ProfileDetails, newItem: ProfileDetails): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ProfileDetails, newItem: ProfileDetails): Any? {
        if (oldItem.login.uuid == newItem.login.uuid) {
            return if (oldItem.action == newItem.action) {
                super.getChangePayload(oldItem, newItem)
            } else {
                val diff = Bundle()
                diff.putInt(ARG_ACTION, newItem.action!!)
                diff
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }
}