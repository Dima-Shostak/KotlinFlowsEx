package com.ds.kotlinflows.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ds.kotlinflows.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), OnClickListener {

	private var _binding: FragmentHomeBinding? = null

	private val binding: FragmentHomeBinding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentHomeBinding.inflate(inflater)
		setListeners()

		return binding.root
	}

	private fun setListeners() = with(binding) {
		userSearchFlow.setOnClickListener(this@HomeFragment)
		gyroSensorFlow.setOnClickListener(this@HomeFragment)
	}

	override fun onClick(v: View?) = with(binding) {
		when (v) {
			userSearchFlow -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserSearchFragment())
			gyroSensorFlow -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGyroFramgment())
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {

		@JvmStatic
		fun newInstance() = HomeFragment()

	}
}