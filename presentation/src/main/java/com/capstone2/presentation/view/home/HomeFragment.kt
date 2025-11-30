package com.capstone2.presentation.view.home

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.capstone2.domain.model.home.RecentFeedback
// GetScoresResult 모델 import 추가
import com.capstone2.domain.model.session.GetScoresResult
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentHomeBinding
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
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
    private val viewModel : GetSessionListViewModel by viewModels()
    private val getScoresViewModel : GetScoresViewModel by viewModels()

    override fun initView() {

        setBottomNav()
        viewModel.getSessionList()
        getScoresViewModel.getScores() // 점수 데이터 요청

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

        // --- Line Chart General Configuration (일반 설정은 그대로 유지) ---
        binding.lcChart.animateXY(700, 700) // X, Y축 기준 0.7초 동안 애니메이션
        // X, Y축 점선 스타일
        binding.lcChart.xAxis.enableGridDashedLine(10f, 5f, 0f) // (실선길이, 공백길이, phase)
        binding.lcChart.axisLeft.enableGridDashedLine(10f, 5f, 0f)

        binding.lcChart.axisLeft.isEnabled = false // 왼쪽 Y축 제거
        binding.lcChart.axisRight.isEnabled = false // 오른쪽 Y축 제거
        binding.lcChart.description.isEnabled = false // 기본 설명 제거
        binding.lcChart.legend.isEnabled = false
        // --- 하드코딩된 데이터 관련 코드는 제거됨 ---
    }

    // API로부터 받은 실제 데이터를 이용해 차트를 설정하는 함수
    private fun setupLineChart(scoresData: List<GetScoresResult>) {
        if (scoresData.isEmpty()) {
            // 데이터가 없을 경우 처리
            return
        }

        // sessionId를 기준으로 정렬 (날짜 순서를 보장하기 위함)
        val sortedData = scoresData.sortedBy { it.sessionId }

        // 1. finalScore (Y값)와 date (X 라벨) 추출
        // finalScore는 Double이므로 Float으로 변환하여 Entry 생성
        val entries = sortedData.mapIndexed { index, scoreResult ->
            Entry(index.toFloat(), scoreResult.finalScore.toFloat())
        }

        val dates = sortedData.map { it.date }

        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary)

        // 2. 데이터셋 만들기
        val dataSet = LineDataSet(entries, "일주일 점수").apply {
            color = primaryColor      // 선 색상
            lineWidth = 3f                           // 선 두께
            circleRadius = 4f                        // 점 크기
            setCircleColor(primaryColor)                // 점 색상
            setDrawValues(true)                      // 값 텍스트 표시 여부
            valueTextSize = 12f                      // 값 텍스트 크기
            valueTextColor = Color.BLACK             // 값 텍스트 색상
            mode = LineDataSet.Mode.LINEAR     // 선을 곡선으로

            // 선 아래 채우기
            setDrawFilled(true)
            fillAlpha = 30
            fillColor = primaryColor
        }

        // 3. 차트에 데이터 넣기
        val lineData = LineData(dataSet)
        binding.lcChart.data = lineData

        // 4. X축 라벨 커스텀 (날짜 표시)
        binding.lcChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(dates)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            textSize = 12f
        }

        // 5. 차트 갱신
        binding.lcChart.invalidate()
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.getSessionState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("${it.data}")
                    val presentations = it.data
                    if (it.data.isNotEmpty()) {
                        val adapter = MyRecentPresentationRvAdapter(presentations)
                        binding.rvMyRecentPresentation.adapter = adapter
                        binding.rvMyRecentPresentation.layoutManager = LinearLayoutManager(requireContext())
                        binding.tvMyPresentationNothing.visibility = View.INVISIBLE
                    } else {
                        binding.tvMyPresentationNothing.visibility = View.VISIBLE
                    }
                }
                is UiState.Error -> {
                    showToast("정보 조회에 실패했습니다.")
                }
            }
        }

        getScoresViewModel.getScoresState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("Scores fetched: ${it.data}")
                    // 성공 시, 실제 데이터로 차트 설정 함수 호출
                    setupLineChart(it.data)
                }
                is UiState.Error -> {
                    showToast("점수 조회에 실패했습니다.")
                }
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