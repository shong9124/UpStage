package com.capstone2.presentation.view.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.usecase.auth.LoginUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _loginState = MutableLiveData<UiState<Boolean>>(UiState.Loading)

    val loginState: LiveData<UiState<Boolean>> get() = _loginState

    fun login(email: String, password:String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            loginUseCase.invoke(email, password)
                .onSuccess { _loginState.value = UiState.Success(it) }
                .onFailure { _loginState.value = UiState.Error(it.message.toString()) }
        }
    }
}