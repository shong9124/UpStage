package com.capstone2.presentation.view.home

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var timeJob: Job? = null

    override fun initView() {

        setBottomNav()

    }

    private fun moveToNext(route: NavigationRoutes){
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }

    private fun setBottomNav(){
        binding.bottomNav.ivBackstage.setImageResource(R.drawable.ic_backstage_able)
        binding.bottomNav.tvBackstage.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))

        binding.bottomNav.menuPresentation.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.Presentation)
                )
            }
        }

//        binding.bottomNav.menuMyPage.setOnClickListener {
//            timeJob?.cancel()
//            lifecycleScope.launch {
//                navigationManager.navigate(
//                    NavigationCommand.ToRoute(NavigationRoutes.MyPage)
//                )
//            }
//        }
    }
}