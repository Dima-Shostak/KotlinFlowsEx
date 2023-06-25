package com.ds.kotlinflows.data.user_search.di

import com.ds.kotlinflows.data.user_search.UserApi
import com.ds.kotlinflows.data.user_search.UserRepositoryImpl
import com.ds.kotlinflows.domain.user_search.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userModule = module {
	single { UserApi(androidContext()) }
	single<UserRepository> { UserRepositoryImpl(get<UserApi>()) }
}