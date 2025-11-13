package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.usecase.auth.SessionUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase
): ViewModel() {

    private val _sessionState = MutableLiveData<UiState<CreateSession>>()

    val sessionState : LiveData<UiState<CreateSession>> get() = _sessionState

    fun createSession(modelVersion: String, title: String) {
        _sessionState.value = UiState.Loading

        viewModelScope.launch {
            sessionUseCase.invoke(modelVersion, title)
                .onSuccess { _sessionState.value = UiState.Success(it) }
                .onFailure { _sessionState.value = UiState.Error(it.message.toString()) }
        }
    }
}