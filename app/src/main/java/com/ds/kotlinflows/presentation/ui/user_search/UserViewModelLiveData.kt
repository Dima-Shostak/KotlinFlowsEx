package com.ds.kotlinflows.presentation.ui.user_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class UserViewModelLiveData(private val repository: UserRepository<List<UserItem>>) : ViewModel() {

	private val _searchResults = MutableLiveData<List<UserItem>>()
	val searchResults: LiveData<List<UserItem>> get() = _searchResults

	private var searchInputJob: Job? = null

	fun setSearchQuery(query: String) {
		searchUsers(query)
	}

	private fun searchUsers(query: String) {
		searchInputJob?.cancel()
		searchInputJob = viewModelScope.launch {
			delay(300L)

			try {
				val results = repository.searchUsers(query)
				_searchResults.postValue(results)
			} catch (e: Exception) {
				// Handle error
			}
		}
	}
}