package com.capstone2.presentation.view.sign

import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignUpCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpCompleteFragment : BaseFragment<FragmentSignUpCompleteBinding>() {
    override fun initView() {
        binding.btnSignUpComplete.setOnClickListener {
            moveToNext(NavigationRoutes.SignIn)
        }
    }

    private fun moveToNext(route: NavigationRoutes){
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }
}