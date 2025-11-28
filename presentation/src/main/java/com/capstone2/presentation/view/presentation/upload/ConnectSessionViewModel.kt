package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.ConnectSessionResult
import com.capstone2.domain.usecase.session.ConnectSessionUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectSessionViewModel @Inject constructor(
    private val connectSessionUseCase: ConnectSessionUseCase
): ViewModel() {

    private val _connectState = MutableLiveData<UiState<ConnectSessionResult>>()

    val connectState : LiveData<UiState<ConnectSessionResult>> get() = _connectState

    fun connectSession(id: Int, body: ConnectSession) {
        _connectState.value = UiState.Loading

        viewModelScope.launch {
            connectSessionUseCase.invoke(id, body)
                .onSuccess { _connectState.value = UiState.Success(it) }
                .onFailure { _connectState.value = UiState.Error(it.message.toString()) }
        }
    }
}