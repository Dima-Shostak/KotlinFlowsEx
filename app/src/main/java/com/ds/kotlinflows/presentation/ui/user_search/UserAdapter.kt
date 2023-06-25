package com.ds.kotlinflows.presentation.ui.user_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ds.kotlinflows.R
import com.ds.kotlinflows.domain.user_search.UserItem

class UserAdapter : ListAdapter<UserItem, UserAdapter.UserViewHolder>(UserDiffCallback()) {

	fun setList(list: List<UserItem>) {
		submitList(null)
		submitList(list)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.user_item, parent, false)
		return UserViewHolder(view)
	}

	override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
		val user = getItem(position)
		holder.bind(user)
	}

	class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val nameTextView: TextView = itemView.findViewById(R.id.name)
		private val emailTextView: TextView = itemView.findViewById(R.id.email)

		fun bind(user: UserItem) {
			nameTextView.text = user.name
			emailTextView.text = user.email
		}
	}

	class UserDiffCallback : DiffUtil.ItemCallback<UserItem>() {
		override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
			return oldItem.email == newItem.email
		}

		override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
			return oldItem == newItem
		}
	}
}
