package com.capstone2.presentation.view.presentation.upload

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
            moveToNext(NavigationRoutes.PresentationResult)
        }

    }

    private fun moveToNext(route: NavigationRoutes){
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }

    private fun setBottomNav(){
        binding.bottomNav.ivPresentation.setImageResource(R.drawable.ic_lamp_able)
        binding.bottomNav.tvPresentation.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))

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