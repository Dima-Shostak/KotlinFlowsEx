package com.ds.kotlinflows.presentation.ui.user_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class UserViewModelFlow(private val repository: UserRepository<Flow<List<UserItem>>>) : ViewModel() {

	private val _searchQuery = MutableStateFlow("")
	private val searchQuery: StateFlow<String> get() = _searchQuery


	/**
	 * Here is a breakdown of the operations:
	 *
	 * * debounce(300): This operator is used to prevent the search from being triggered too frequently. It will wait until there's a pause of 300ms in the emissions. For example, if a user is typing a search query, it will wait until the user has stopped typing for 300ms before emitting the latest value.
	 *
	 * * distinctUntilChanged(): This operator will ensure the flow does not emit the same value consecutively. In the context of a search query, it ensures that the search function isn't invoked multiple times with the same query string.
	 *
	 * * flatMapLatest { repository.searchUsers(it) }: This is where the search operation actually happens. For each search query string, searchUsers is called which returns a Flow of search results. If a new search query is emitted before the previous one completes, flatMapLatest cancels the previous search operation and immediately switches to the new one. This ensures that the results delivered are always from the latest search query.
	 *
	 * * catch { /* handle potential errors here */ }: This operator is used to handle any exceptions that may occur during the collection of the flow. If an exception is thrown in the searchUsers function, for example, this catch block will be invoked.
	 *
	 * * asLiveData(): This is used to convert the Flow to LiveData. LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This ensures the UI matches the current state of the data.
	 */
	@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class) val searchResults = searchQuery
		.debounce(500)  // waits for 600ms pause in typing
		.distinctUntilChanged() // only search if the query is changed
		.flatMapLatest { repository.searchUsers(it) }
		.catch { /* handle potential errors here */ }
		.asLiveData() // switch to LiveData for consumption by UI layer

	fun setSearchQuery(query: String) {
		_searchQuery.value = query
	}

}