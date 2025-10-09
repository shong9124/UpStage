package com.capstone2.presentation.view.home

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.capstone2.domain.model.home.RecentFeedback
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var timeJob: Job? = null
    private lateinit var recentFeedbackAdapter: RecentFeedbackRvAdapter

    override fun initView() {

        setBottomNav()

        val items = listOf(
            RecentFeedback("발표속도가 너무 빨라요.", "#발표속도"),
            RecentFeedback("어... 음... 등의 잉여표현이 많아요", "#잉여표현")
        )

        recentFeedbackAdapter = RecentFeedbackRvAdapter(items)
        binding.vpMyFeedback.adapter = recentFeedbackAdapter
        binding.vpMyFeedback.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.vpMyFeedback.offscreenPageLimit = 4
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages =
            resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        binding.vpMyFeedback.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
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
        binding.bottomNav.ivBackstage.setImageResource(R.drawable.ic_backstage_able)
        binding.bottomNav.tvBackstage.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            )
        )

        binding.bottomNav.menuPresentation.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.Presentation)
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