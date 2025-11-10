package com.capstone2.presentation.view.sign

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignUpBinding
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun initView() {
        binding.btnSubmit.setOnClickListener {
            var allFilled = true

            val etName = binding.etName
            val etEmail = binding.etEnterEmail
            val etPw = binding.etEnterPw

            val editTextList = listOf(etName, etEmail, etPw)

            for (editText in editTextList) {
                val text = editText.text.toString().trim()

                if (text.isEmpty()) {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke_error
                    )
                    allFilled = false
                } else if (editText == etEmail && !android.util.Patterns.EMAIL_ADDRESS.matcher(text)
                        .matches()
                ) {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke_error
                    )
                    Toast.makeText(requireContext(), "이메일 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
                    allFilled = false
                } else {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke
                    )
                }
            }
            if (!allFilled) {
                if (etName.text.isNullOrBlank() || etPw.text.isNullOrBlank() || etEmail.text.isNullOrBlank()) {
                    Toast.makeText(requireContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener
            }
            // viewModel
            LoggerUtil.d("회원 가입 시도")
            viewModel.signUp(
                binding.etEnterEmail.text.toString(),
                binding.etEnterPw.text.toString(),
                binding.etName.text.toString()
            )
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.signUpState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val route = NavigationRoutes.SignUpComplete
                    moveToNext(route)
                }
                is UiState.Error -> {
                    showToast("회원가입에 실패했습니다.")
                }
            }
        }
    }

    private fun validatePassword() {
        val password = binding.etEnterPw.text.toString()
        val pattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

        if (pattern.matches(password)) {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text_type_stroke)
            binding.btnSubmit.isEnabled = true
        } else {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text_type_stroke_error)
            binding.btnSubmit.isEnabled = false
            Toast.makeText(requireContext(), "비밀번호는 8자 이상이며 영문자와 숫자를 포함해야 합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }
}