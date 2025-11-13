package com.capstone2.presentation.view.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.auth.GetUserInfo
import com.capstone2.domain.usecase.auth.GetUserInfoUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private  val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _getUserInfoState = MutableLiveData<UiState<GetUserInfo>>()

    val getUserInfoState: LiveData<UiState<GetUserInfo>> get() = _getUserInfoState

    fun getUserInfo() {
        _getUserInfoState.value = UiState.Loading

        viewModelScope.launch {
            getUserInfoUseCase.invoke()
                .onSuccess { _getUserInfoState.value = UiState.Success(it) }
                .onFailure { _getUserInfoState.value = UiState.Error(it.message.toString()) }
        }
    }
}