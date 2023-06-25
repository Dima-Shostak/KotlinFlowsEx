package com.ds.kotlinflows.data.gyro

import com.ds.kotlinflows.domain.gyro.GyroSensorRepository
import com.ds.kotlinflows.domain.gyro.RotationEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

/**
 * An implementation of the [GyroSensorRepository] interface.
 *
 * @property gyroApi An instance of [GyroApi] used to interface with the gyroscope.
 */
class GyroSensorRepositoryImpl(private val gyroApi: GyroApi) : GyroSensorRepository {

	/**
	 * Represents a stream of rotation events from the gyroscope sensor, delivered through [GyroApi].
	 *
	 * The Flow starts producing values when it is collected. Once collected, it subscribes a listener to
	 * the [GyroApi]. When a rotation event occurs, the listener sends the event to the Flow.
	 *
	 * The Flow stops producing values and unsubscribes the listener from [GyroApi] when the collector is
	 * either cancelled or experiences an exception.
	 *
	 * Note: The Flow executes on the IO dispatcher. Therefore, the rotation events are emitted on a
	 * background thread. It's safe to collect this Flow on the main thread.
	 *
	 * @return A [Flow] that emits [RotationEvent]s.
	 */
	override val gyroSensorRotationFlow: Flow<RotationEvent> = callbackFlow {
		val gyroListener: (RotationEvent) -> Unit = {
			trySend(it)
		}

		gyroApi.subscribe(gyroListener)

		awaitClose { gyroApi.unsubscribe(gyroListener) }

	}.flowOn(Dispatchers.IO)
}
