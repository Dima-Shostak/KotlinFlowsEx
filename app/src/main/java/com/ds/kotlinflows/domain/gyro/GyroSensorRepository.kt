package com.ds.kotlinflows.domain.gyro

import kotlinx.coroutines.flow.Flow

interface GyroSensorRepository {
	val gyroSensorRotationFlow: Flow<RotationEvent>
}