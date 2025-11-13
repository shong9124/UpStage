package com.capstone2.presentation.view.my

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone2.domain.model.my.MyPresentationRecord
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentMyPageBinding
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val viewModel : UserInfoViewModel by viewModels()

    private var timeJob: Job? = null

    override fun initView() {

        setBottomNav()

        viewModel.getUserInfo()

        val itemList =
            listOf(
                MyPresentationRecord("test title1", 80),
                MyPresentationRecord("test title2", 90),
                MyPresentationRecord("test title3", 100)
            )

        val adapter = MyPresentationRecordRvAdapter(itemList)

        binding.rvMyRecord.adapter = adapter
        binding.rvMyRecord.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.getUserInfoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    binding.tvUserName.text = it.data.nickname + " 님"
                }
                is UiState.Error -> {
                    showToast("정보 조회에 실패했습니다.")
                }
            }
        }
    }

    private fun setBottomNav() {
        binding.bottomNav.ivMyPage.setImageResource(R.drawable.ic_my_page_able)
        binding.bottomNav.tvMyPage.setTextColor(
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

        binding.bottomNav.menuBackstage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.Home)
                )
            }
        }
    }

}