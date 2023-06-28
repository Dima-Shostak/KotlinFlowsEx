package com.ds.kotlinflows.presentation.ui.gyro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ds.kotlinflows.databinding.FragmentGyroFramgmentBinding
import com.ds.kotlinflows.domain.gyro.RotationEvent
import com.ds.kotlinflows.presentation.SmpLog
import com.ds.kotlinflows.presentation.springOf
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [GyroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GyroFragment : Fragment() {

	private var _binding: FragmentGyroFramgmentBinding? = null
	private val binding: FragmentGyroFramgmentBinding
		get() = _binding!!

	private val kfViewModel: GyroFlowViewModel by viewModel()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentGyroFramgmentBinding.inflate(inflater)
		observeGyroEvents()

		return binding.root
	}


	private fun observeGyroEvents() = lifecycleScope.launch {
		kfViewModel.gyroSensor.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { event ->
			SmpLog.i(event.toString())
			rotateShape(event)
		}
	}

	private fun rotateShape(event: RotationEvent) = with(binding) {
		val rotationXSpring = springOf(shape, SpringAnimation.ROTATION_X)
		val rotationSpring = springOf(shape, SpringAnimation.ROTATION)

		rotationXSpring.animateToFinalPosition((event.pitch * 2F) - 90)
		rotationSpring.animateToFinalPosition(-event.roll / 1.5F)

		val sb = StringBuilder()
		sb.appendLine(event.azimuth)
		sb.appendLine(event.pitch)
		sb.appendLine(event.roll)
		infoTv.text = sb
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {
		@JvmStatic
		fun newInstance() = GyroFragment()
	}
}