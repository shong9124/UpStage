package com.capstone2.presentation.view.presentation.result

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentPresentationResultBinding
import com.capstone2.presentation.util.UiState
import com.capstone2.presentation.view.presentation.upload.AiAnalysisViewModel
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PresentationResultFragment : BaseFragment<FragmentPresentationResultBinding>() {

    // 🚨 추가: Activity Scope의 ViewModel을 가져와 공유
    private val aiAnalysisViewModel: AiAnalysisViewModel by viewModels(
        ownerProducer = { requireActivity() } // Activity Scope로 지정
    )

    override fun initView() {

        binding.btnReturnToHome.setOnClickListener {
            moveToNext(NavigationRoutes.Home)
        }

        startAnimation()

    }

    // 🌟 Observer 메서드 추가
    override fun setObserver() {
        super.setObserver()

        aiAnalysisViewModel.aiAnalysisState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // 분석 중 애니메이션 시작 또는 로딩 표시
                }
                is UiState.Success -> {
                    LoggerUtil.d("분석 결과를 성공적으로 받아왔습니다.") // 🌟 요청하신 로깅

                    // 🌟 AI 분석 결과 (it.data: AiAnalysisResult)를 화면에 표시
                    // 예: binding.tvScore.text = it.data.scoreMetrics.finalScore.toString()

                    startAnimation()
                }
                is UiState.Error -> {
                    showToast("분석 결과를 불러오는 데 실패했습니다: ${it.message}")
                    LoggerUtil.e("분석 결과를 불러오는 데 실패했습니다: ${it.message}")
                    // 에러 처리 및 홈으로 복귀 등
                }
            }
        }
    }

    private fun startAnimation() {
        showStepWithBlink(binding.ivDash1, binding.groupStep1) {
            binding.ivDash1.visibility = View.VISIBLE
            showStepWithBlink(binding.ivDash2, binding.groupStep2) {
                binding.ivDash2.visibility = View.VISIBLE
                showStepWithBlink(binding.ivStep3, binding.groupStep3) {
                    binding.groupResult.visibility = View.VISIBLE
                    binding.groupFeedback1.visibility = View.VISIBLE
                    binding.groupFeedback2.visibility = View.VISIBLE
                    binding.groupFeedbackResult.visibility = View.VISIBLE
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
        blink.repeatCount = 8
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