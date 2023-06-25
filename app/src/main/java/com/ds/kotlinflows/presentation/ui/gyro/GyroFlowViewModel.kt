package com.ds.kotlinflows.presentation.ui.gyro

import androidx.lifecycle.ViewModel
import com.ds.kotlinflows.domain.gyro.GyroSensorRepository
import kotlinx.coroutines.flow.distinctUntilChanged

class GyroFlowViewModel(gyroSensorRepository: GyroSensorRepository) : ViewModel() {
	val gyroSensor = gyroSensorRepository
		.gyroSensorRotationFlow
		.distinctUntilChanged()
}