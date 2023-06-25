package com.ds.kotlinflows.data.gyro

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.ds.kotlinflows.domain.gyro.RotationEvent
import com.ds.kotlinflows.presentation.SmpLog

class GyroApi(context: Context) {

	private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
	private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

	private val listeners = mutableListOf<(RotationEvent) -> Unit>()
	private var sensorEventListener: SensorEventListener? = null

	init {
		if (sensor == null) {
			throw IllegalStateException("Device does not support Rotation Vector Sensor")
		}
	}

	fun subscribe(listener: (RotationEvent) -> Unit) {
		if (listeners.isEmpty()) {
			sensorEventListener = createSensorEventListener()
			sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
		}
		listeners.add(listener)

		SmpLog.i("subscribed")
	}

	fun unsubscribe(listener: (RotationEvent) -> Unit) {
		listeners.remove(listener)
		if (listeners.isEmpty()) {
			sensorManager.unregisterListener(sensorEventListener)
			sensorEventListener = null
		}

		SmpLog.i("unsubscribed")
	}

	private fun createSensorEventListener(): SensorEventListener {
		return object : SensorEventListener {
			override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
				// We are not interested in accuracy changes
			}

			override fun onSensorChanged(event: SensorEvent?) {
				event?.let { sensorEvent ->
					val rotationMatrix = FloatArray(9)
					SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values)
					val orientation = FloatArray(3)
					SensorManager.getOrientation(rotationMatrix, orientation)

					val azimuthInRadians = orientation[0]
					val pitchInRadians = orientation[1]
					val rollInRadians = orientation[2]

					val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
					val pitchInDegrees = Math.toDegrees(pitchInRadians.toDouble()).toFloat()
					val rollInDegrees = Math.toDegrees(rollInRadians.toDouble()).toFloat()

					val rotationEvent = RotationEvent(azimuthInDegrees, pitchInDegrees, rollInDegrees)
					listeners.forEach { it.invoke(rotationEvent) }
				}
			}
		}
	}
}
