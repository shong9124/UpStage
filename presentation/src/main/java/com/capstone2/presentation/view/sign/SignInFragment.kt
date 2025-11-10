package com.capstone2.presentation.view.sign

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignInBinding
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {

        binding.btnLogin.setOnClickListener {
            LoggerUtil.d("로그인 시도")
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.tvSignUp.setOnClickListener{
            moveToNext(NavigationRoutes.SignUp)
        }

    }

    override fun setObserver() {
        super.setObserver()

        viewModel.loginState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val route = NavigationRoutes.Home
                    moveToNext(route)
                }
                is UiState.Error -> {
                    showToast("로그인에 실패했습니다.")
                }
            }
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