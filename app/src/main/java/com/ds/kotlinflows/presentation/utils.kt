package com.ds.kotlinflows.presentation

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

fun springOf(
	view: View, animation: DynamicAnimation.ViewProperty, springForce: SpringForce = SpringForce().apply {
		stiffness = 50F
		dampingRatio = .3F
	}
): SpringAnimation {
	return SpringAnimation(view, animation).apply {
		spring = springForce
	}
}

fun Activity.closeKeyboard() {
	val view = currentFocus
	if (view != null) {
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(view.windowToken, 0)
	}
}
