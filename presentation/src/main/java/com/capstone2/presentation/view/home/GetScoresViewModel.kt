package com.capstone2.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.GetScoresResult
import com.capstone2.domain.usecase.session.GetScoresUseCase
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetScoresViewModel @Inject constructor(
    private val getScoresUseCase: GetScoresUseCase
): ViewModel() {

    private val _getScoresState = MutableLiveData<UiState<List<GetScoresResult>>>()
    val getScoresState : LiveData<UiState<List<GetScoresResult>>> get() = _getScoresState

    fun getScores() {
        _getScoresState.value = UiState.Loading

        viewModelScope.launch {
            getScoresUseCase.invoke()
                .onSuccess {
                    LoggerUtil.i("GetScores UseCase SUCCESS. Data size: ${it.size}")
                    _getScoresState.value = UiState.Success(it)
                }
                .onFailure {
                    LoggerUtil.e("GetScores UseCase FAILURE. Error: ${it.message}")
                    _getScoresState.value = UiState.Error(it.message.toString())
                }
        }
    }
}