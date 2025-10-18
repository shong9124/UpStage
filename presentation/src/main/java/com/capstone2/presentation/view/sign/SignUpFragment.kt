package com.capstone2.presentation.view.sign

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
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
            moveToNext(NavigationRoutes.SignUpComplete)
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