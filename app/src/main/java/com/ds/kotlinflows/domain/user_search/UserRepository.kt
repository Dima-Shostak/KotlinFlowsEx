package com.ds.kotlinflows.domain.user_search

interface UserRepository<T> {
	suspend fun searchUsers(query: String): T
}