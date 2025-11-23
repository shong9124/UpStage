package com.capstone2.presentation.view.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.repository.TokenRepository
import com.capstone2.domain.usecase.auth.LoginUseCase
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val loginState: LiveData<UiState<Boolean>> get() = _loginState

    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            loginUseCase.invoke(email, password)
                .onSuccess { loginResult ->
                    _loginState.value = UiState.Success(true)

                    // 서버에서 받은 userId 저장
                    loginResult.userId?.let { id ->

                        // DataStore 관련 작업을 하나의 runCatching 블록으로 묶어 처리합니다.
                        kotlin.runCatching {
                            // saveUserId의 Result를 getOrThrow()로 처리하여 실패 시 예외를 던집니다.
                            withContext(NonCancellable) {
                                tokenRepository.saveUserId(id).getOrThrow()

                                val savedId = tokenRepository.getUserId().first().userId
                                LoggerUtil.d("userId from server: $id")
                                LoggerUtil.d("savedID in DataStore: $savedId")
                            }

                        }.onFailure { exception ->
                            // Job was cancelled는 CancellationException에 해당하며,
                            // 이는 코루틴의 정상적인 취소 흐름이므로 비즈니스 에러로 로깅하지 않습니다.
                            if (exception is CancellationException) {
                                // 취소 예외는 다시 던져서 코루틴 계층에 전파합니다.
                                throw exception
                            }

                            // 그 외의 I/O 오류 등 실제 DataStore 저장 실패만 로깅합니다.
                            LoggerUtil.e("DataStore operation failed (saving/reading userId): ${exception.message}")
                        }
                    }


                }
                .onFailure { exception ->
                    _loginState.value = UiState.Error(exception.message.toString())
                    LoggerUtil.e("Login failed: ${exception.message}")
                }
        }
    }
}
