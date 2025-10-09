package com.capstone2.presentation.view.my

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private var timeJob: Job? = null

    override fun initView() {

        setBottomNav()
    }

    private fun setBottomNav(){
        binding.bottomNav.ivMyPage.setImageResource(R.drawable.ic_my_page_able)
        binding.bottomNav.tvMyPage.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))

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