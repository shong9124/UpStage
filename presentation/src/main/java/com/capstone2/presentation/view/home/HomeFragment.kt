package com.capstone2.presentation.view.home

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.capstone2.domain.model.home.RecentFeedback
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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

        val scores = listOf(80, 75, 90, 85, 70, 95, 88)
        val dates = listOf("09-15", "09-16", "09-17", "09-18", "09-19", "09-20", "09-21")

// 점수 → Entry 변환
        val entries = scores.mapIndexed { index, score ->
            Entry(index.toFloat(), score.toFloat())
        }

// 데이터셋 만들기
        val dataSet = LineDataSet(entries, "일주일 점수").apply {
            color = R.color.primary      // 선 색상
            lineWidth = 3f                           // 선 두께
            circleRadius = 4f                        // 점 크기
            setCircleColor(R.color.primary)                // 점 색상
            setDrawValues(true)                      // 값 텍스트 표시 여부
            valueTextSize = 12f                      // 값 텍스트 크기
            valueTextColor = Color.BLACK             // 값 텍스트 색상
            mode = LineDataSet.Mode.LINEAR     // 선을 곡선으로

            // 선 아래 채우기
            setDrawFilled(true)
            fillAlpha = 30
            fillColor = R.color.primary
        }

// 차트에 데이터 넣기
        val lineData = LineData(dataSet)
        binding.lcChart.data = lineData

// X축 라벨 커스텀 (날짜 표시)
        binding.lcChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(dates)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            textSize = 12f
        }

        // 기타 설정
        binding.lcChart.animateXY(700, 700) // X, Y축 기준 0.7초 동안 애니메이션
        // X, Y축 점선 스타일
        binding.lcChart.xAxis.enableGridDashedLine(10f, 5f, 0f) // (실선길이, 공백길이, phase)
        binding.lcChart.axisLeft.enableGridDashedLine(10f, 5f, 0f)

        binding.lcChart.axisLeft.isEnabled = false // 왼쪽 Y축 제거
        binding.lcChart.axisRight.isEnabled = false // 오른쪽 Y축 제거
        binding.lcChart.description.isEnabled = false // 기본 설명 제거
        binding.lcChart.legend.isEnabled = false
        binding.lcChart.invalidate() // 차트 갱신

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