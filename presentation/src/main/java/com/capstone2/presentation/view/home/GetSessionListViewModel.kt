package com.capstone2.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.usecase.session.GetSessionUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetSessionListViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase
): ViewModel() {
    private val _getSessionState = MutableLiveData<UiState<List<GetSessionList>>>()

    val getSessionState : LiveData<UiState<List<GetSessionList>>> get() = _getSessionState

    fun getSessionList() {
        _getSessionState.value = UiState.Loading

        viewModelScope.launch {
            getSessionUseCase.invoke()
                .onSuccess { _getSessionState.value = UiState.Success(it) }
                .onFailure { _getSessionState.value = UiState.Error(it.message.toString()) }
        }
    }
}