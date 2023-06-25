package com.ds.kotlinflows.domain.user_search

import kotlinx.coroutines.flow.Flow

interface UserRepository {
	fun searchUsers(query: String): Flow<List<UserItem>>
}