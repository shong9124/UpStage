package com.capstone2.presentation.view.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.GetHistoryResult
import com.capstone2.domain.usecase.my.GetHistoryUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetHistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _getHistoryState = MutableLiveData<UiState<List<GetHistoryResult>>>()

    val getHistoryState : LiveData<UiState<List<GetHistoryResult>>> get() = _getHistoryState

    fun getHistory() {
        _getHistoryState.value = UiState.Loading

        viewModelScope.launch {
            getHistoryUseCase.invoke()
                .onSuccess { _getHistoryState.value = UiState.Success(it) }
                .onFailure { _getHistoryState.value = UiState.Error(it.message.toString()) }
        }
    }
}