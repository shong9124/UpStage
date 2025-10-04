package com.capstone2.presentation.view.sign

import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun initView() {
        binding.btnSubmit.setOnClickListener {
            moveToNext(NavigationRoutes.SignUpComplete)
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