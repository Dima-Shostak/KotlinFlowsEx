package com.ds.kotlinflows.presentation.ui.user_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import kotlinx.coroutines.launch

class UserViewModelLiveData(private val repository: UserRepository<List<UserItem>>) : ViewModel() {

	private val _searchResults = MutableLiveData<List<UserItem>>()
	val searchResults: LiveData<List<UserItem>> get() = _searchResults

	fun setSearchQuery(query: String) {
		searchUsers(query)
	}

	private fun searchUsers(query: String) {
		viewModelScope.launch {
			try {
				val results = repository.searchUsers(query)
				_searchResults.postValue(results)
			} catch (e: Exception) {
				// Handle error
			}
		}
	}
}