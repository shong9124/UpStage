package com.capstone2.presentation.view.sign

import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override fun initView() {

        binding.btnLogin.setOnClickListener {

        }

        binding.tvSignUp.setOnClickListener{
            moveToNext(NavigationRoutes.SignUp)
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