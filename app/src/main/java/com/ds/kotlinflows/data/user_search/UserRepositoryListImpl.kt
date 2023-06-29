package com.ds.kotlinflows.data.user_search

import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository

class UserRepositoryListImpl(private val userApi: UserApi) : UserRepository<List<UserItem>> {

	override suspend fun searchUsers(query: String): List<UserItem> {
		return userApi.searchQuery(query)
	}

}