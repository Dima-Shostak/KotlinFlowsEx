package com.ds.kotlinflows.presentation.ui.user_search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.kotlinflows.databinding.FragmentTextSearchBinding
import com.ds.kotlinflows.presentation.SmpLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserSearchFragment : Fragment() {

	private var _binding: FragmentTextSearchBinding? = null
	private val binding: FragmentTextSearchBinding
		get() = _binding!!

	private val viewModel: UserViewModelLiveData by viewModel()
	private lateinit var userAdapter: UserAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTextSearchBinding.inflate(inflater)

		initResultList()
		observeSearchResults()
		setListeners()

		return binding.root
	}

	private fun initResultList() = with(binding) {
		userAdapter = UserAdapter()
		resultOutput.layoutManager = LinearLayoutManager(context)
		resultOutput.adapter = userAdapter
	}

	private fun setListeners() = with(binding) {
		searchQueryInput.doOnTextChanged { input, _, _, _ ->
			viewModel.setSearchQuery(input.toString())
		}
	}

	@SuppressLint("SetTextI18n")
	private fun observeSearchResults() {
		viewModel.searchResults.observe(viewLifecycleOwner) { users ->
			SmpLog.d("Users search result: $users")
			userAdapter.setList(users)

			binding.resultsCount.apply {
				alpha = 0F
				scaleY = 0F
				scaleX = 1.5F
				text = "result count: ${users.size}"
				animate().alpha(1F).scaleX(1F).scaleY(1F).setDuration(150L).start()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {
		@JvmStatic
		fun newInstance() = UserSearchFragment()
	}
}