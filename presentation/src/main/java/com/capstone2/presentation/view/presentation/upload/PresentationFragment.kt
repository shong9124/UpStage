package com.capstone2.presentation.view.presentation.upload

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentPresentationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PresentationFragment : BaseFragment<FragmentPresentationBinding>() {

    private var timeJob: Job? = null

    override fun initView() {

        setBottomNav()

        binding.btnSubmitP.setOnClickListener {
            var allFilled = true

            val editTextList = listOf(
                binding.etText,
                binding.etTitle,
            )

            for (editText in editTextList) {
                if (editText.text.toString().trim().isEmpty()) {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke_error
                    )
                    allFilled = false
                } else {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke
                    )
                }
            }
            if (!allFilled) {
                Toast.makeText(requireContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            moveToNext(NavigationRoutes.PresentationResult)
        }
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }

    private fun setBottomNav() {
        binding.bottomNav.ivPresentation.setImageResource(R.drawable.ic_lamp_able)
        binding.bottomNav.tvPresentation.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            )
        )

        binding.bottomNav.menuBackstage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.Home)
                )
            }
        }

        binding.bottomNav.menuMyPage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.MyPage)
                )
            }
        }
    }

}