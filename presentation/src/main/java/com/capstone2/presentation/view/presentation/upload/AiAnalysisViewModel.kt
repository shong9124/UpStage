package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.ai.AiAnalysisResult
import com.capstone2.domain.usecase.session.AiAnalysisUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiAnalysisViewModel @Inject constructor(
    private val aiAnalysisUseCase: AiAnalysisUseCase
): ViewModel() {

    private val _aiAnalysisState = MutableLiveData<UiState<AiAnalysisResult>>()

    val aiAnalysisState : LiveData<UiState<AiAnalysisResult>> get() = _aiAnalysisState

    fun aiAnalysis(sessionId: Int) {
        _aiAnalysisState.value = UiState.Loading

        viewModelScope.launch {
            aiAnalysisUseCase.invoke(sessionId)
                .onSuccess { _aiAnalysisState.value = UiState.Success(it) }
                .onFailure { _aiAnalysisState.value = UiState.Error(it.message.toString()) }
        }
    }
}