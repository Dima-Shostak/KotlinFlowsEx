package com.ds.kotlinflows.presentation.di

import com.ds.kotlinflows.domain.user_search.UserItem
import com.ds.kotlinflows.domain.user_search.UserRepository
import com.ds.kotlinflows.presentation.ui.gyro.GyroFlowViewModel
import com.ds.kotlinflows.presentation.ui.user_search.UserViewModelFlow
import com.ds.kotlinflows.presentation.ui.user_search.UserViewModelLiveData
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiModule = module {
	viewModelOf(::GyroFlowViewModel)
	viewModel { UserViewModelFlow(get<UserRepository<Flow<List<UserItem>>>>(named("flow"))) }
	viewModel { UserViewModelLiveData(get<UserRepository<List<UserItem>>>(named("list"))) }
}