package com.ds.kotlinflows.data.user_search.di

import com.ds.kotlinflows.data.user_search.UserApi
import com.ds.kotlinflows.data.user_search.UserRepositoryFlowImpl
import com.ds.kotlinflows.data.user_search.UserRepositoryListImpl
import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userModule = module {
	single { UserApi(androidContext()) }
	single<UserRepository<Flow<List<UserItem>>>>(named("flow")) { UserRepositoryFlowImpl(get<UserApi>()) }
	single<UserRepository<List<UserItem>>>(named("list")) { UserRepositoryListImpl(get<UserApi>()) }
}