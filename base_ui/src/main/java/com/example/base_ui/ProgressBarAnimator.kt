package com.example.base_ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressBarAnimator(
    private val linearProgressIndicator: CircularProgressIndicator,
    private val containerContent: View,
    private val animationDuration: Int = 200
) {

    fun hideProgressBarAndShowContent() {
        linearProgressIndicator.animate()
            .alpha(0f)
            .setDuration(animationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    linearProgressIndicator.visibility = View.GONE
                    containerContent.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate()
                            .alpha(1f)
                            .setDuration(animationDuration.toLong())
                            .setListener(null)
                    }
                }
            })
    }
}