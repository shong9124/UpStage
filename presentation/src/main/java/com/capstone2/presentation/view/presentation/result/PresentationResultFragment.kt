package com.capstone2.presentation.view.presentation.result

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentPresentationResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PresentationResultFragment : BaseFragment<FragmentPresentationResultBinding>() {
    override fun initView() {

        binding.btnReturnToHome.setOnClickListener {
            moveToNext(NavigationRoutes.Home)
        }

        startAnimation()

    }

    private fun startAnimation() {
        showStepWithBlink(binding.ivDash1, binding.groupStep1) {
            binding.ivDash1.visibility = View.VISIBLE
            showStepWithBlink(binding.ivDash2, binding.groupStep2) {
                binding.ivDash2.visibility = View.VISIBLE
                showStepWithBlink(binding.ivStep3, binding.groupStep3) {
                    binding.groupResult.visibility = View.VISIBLE
                    binding.ivLogo.visibility = View.INVISIBLE
                    binding.root.setBackgroundResource(R.drawable.bg_result_score)
                    binding.groupStep1.visibility = View.INVISIBLE
                    binding.groupStep2.visibility = View.INVISIBLE
                    binding.groupStep3.visibility = View.INVISIBLE
                    binding.ivDash1.visibility = View.INVISIBLE
                    binding.ivDash2.visibility = View.INVISIBLE

                    requireActivity().window?.apply {
                        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                }
            }
        }
    }

    private fun showStepWithBlink(dash: ImageView, stepGroup: Group, onStepComplete: () -> Unit) {
        val blink = ObjectAnimator.ofFloat(dash, "alpha", 0f, 1f)
        blink.duration = 500
        blink.repeatCount = 4
        blink.repeatMode = ObjectAnimator.REVERSE

        blink.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                dash.visibility = View.VISIBLE
                stepGroup.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                onStepComplete()
            }
        })

        blink.start()
    }

    private fun moveToNext(route: NavigationRoutes){
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }

}