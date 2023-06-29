package com.ds.kotlinflows.data.user_search

import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryFlowImpl(private val userApi: UserApi) : UserRepository<Flow<List<UserItem>>> {

	override suspend fun searchUsers(query: String): Flow<List<UserItem>> {
		return flow {
			emit(userApi.searchQuery(query))
		}
	}

}