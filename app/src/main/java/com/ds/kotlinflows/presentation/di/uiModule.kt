package com.ds.kotlinflows.presentation.di

import com.ds.kotlinflows.domain.user_search.UserRepository
import com.ds.kotlinflows.presentation.ui.gyro.GyroFlowViewModel
import com.ds.kotlinflows.presentation.ui.user_search.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
	viewModelOf(::GyroFlowViewModel)
	viewModel { UserViewModel(get<UserRepository>()) }
}