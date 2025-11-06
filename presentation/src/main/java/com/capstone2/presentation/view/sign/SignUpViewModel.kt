package com.capstone2.presentation.view.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.usecase.SignUpUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val _signUpState = MutableLiveData<UiState<Boolean>>(UiState.Loading)

    val signUpState: LiveData<UiState<Boolean>> get() = _signUpState

    fun signUp(email: String, password:String, name: String) {
        _signUpState.value = UiState.Loading

        viewModelScope.launch {
            signUpUseCase.invoke(email, password, name)
                .onSuccess { _signUpState.value = UiState.Success(it) }
                .onFailure { _signUpState.value = UiState.Error(it.message.toString()) }
        }
    }
}