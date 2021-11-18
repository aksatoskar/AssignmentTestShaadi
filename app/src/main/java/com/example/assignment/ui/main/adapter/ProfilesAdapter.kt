package com.example.assignment.ui.main

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
import com.example.assignment.ui.main.adapter.OnItemClickListener
import io.buildwithnd.demotmdb.util.AppConstants
import io.buildwithnd.demotmdb.util.AppConstants.LOCATION

class ProfilesAdapter:
    ListAdapter<ProfileDetails, ProfilesAdapter.ProfileViewHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ListItemProfileBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilesAdapter.ProfileViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ProfileViewHolder(private val binding: ListItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profileDetails: ProfileDetails, listener: OnItemClickListener?) = with(itemView) {
            binding.tvName.text = "${profileDetails.name?.title} ${profileDetails.name?.first} ${profileDetails.name?.last}"
            profileDetails.dob?.age?.let { age->
                binding.tvAge.visibility = View.VISIBLE
                binding.tvAge.text = binding.root.context.getString(R.string.age, age)
            }

            profileDetails.location?.let { location->
                binding.tvLocation.text = String.format(LOCATION, location.city, location.country)
            }

            profileDetails.picture?.thumbnail?.let { url->
                binding.ivPoster.load(url)
            }

            updateUserActionUI(profileDetails, binding)

            binding.btAccept.setOnClickListener {
                listener?.onItemClick(adapterPosition, AppConstants.ACCEPTED, profileDetails)
            }

            binding.btDecline.setOnClickListener {
                listener?.onItemClick(adapterPosition, AppConstants.DECLINED, profileDetails)
            }
        }

        private fun updateUserActionUI(profileDetails: ProfileDetails, binding: ListItemProfileBinding) {
            when(profileDetails.action) {
                1 -> showUserActionUI(true, binding)
                0 -> showUserActionUI(false, binding)
                else -> {
                    binding.tvAction.visibility = View.GONE
                    binding.btDecline.visibility = View.VISIBLE
                    binding.btAccept.visibility = View.VISIBLE
                }
            }
        }

        private fun showUserActionUI(isAccepted: Boolean, binding: ListItemProfileBinding) {
            binding.tvAction.visibility = View.VISIBLE
            binding.btDecline.visibility = View.GONE
            binding.btAccept.visibility = View.GONE
            if(isAccepted) {
                binding.tvAction.text = binding.root.context.getString(R.string.profile_accepted)
                binding.tvAction.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            } else {
                binding.tvAction.text = binding.root.context.getString(R.string.profile_declined)
                binding.tvAction.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_400))
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
}