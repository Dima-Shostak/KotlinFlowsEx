package com.ds.kotlinflows.data.gyro.di

import com.ds.kotlinflows.data.gyro.GyroApi
import com.ds.kotlinflows.data.gyro.GyroSensorRepositoryImpl
import com.ds.kotlinflows.domain.gyro.GyroSensorRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(DelicateCoroutinesApi::class) val gyroModule = module {
	GlobalScope
	singleOf(::GyroApi)
	singleOf(::GyroSensorRepositoryImpl) { bind<GyroSensorRepository>() }
}

