package com.ds.kotlinflows.presentation

import android.app.Application
import com.ds.kotlinflows.data.gyro.di.gyroModule
import com.ds.kotlinflows.data.user_search.di.userModule
import com.ds.kotlinflows.presentation.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

typealias SmpLog = Timber

class SamplesApplication : Application() {


	override fun onCreate() {
		super.onCreate()

		initTimberLogger()
		startKoin()
	}

	private fun initTimberLogger() {
		Timber.plant(Timber.DebugTree())
	}


	private fun startKoin() {
		startKoin {
			// Log Koin into Android logger
			androidLogger()
			// Reference Android context
			androidContext(this@SamplesApplication)

			// Load modules
			modules(
				gyroModule,
				userModule,
				uiModule,
			)
		}
	}
}