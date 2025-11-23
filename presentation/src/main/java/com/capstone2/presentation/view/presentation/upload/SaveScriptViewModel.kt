package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult
import com.capstone2.domain.usecase.session.SaveScriptUseCase
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveScriptViewModel @Inject constructor(
    private val saveScriptUseCase: SaveScriptUseCase,
): ViewModel() {

    private val _saveScriptState = MutableLiveData<UiState<SaveScriptResult>>()
    val saveScriptState : LiveData<UiState<SaveScriptResult>> get() = _saveScriptState

    fun saveScript(sessionId: Int, request: SaveScript) {
        _saveScriptState.value = UiState.Loading

        viewModelScope.launch {
            saveScriptUseCase.invoke(sessionId, request)
                .onSuccess { _saveScriptState.value = UiState.Success(it) }
                .onFailure { _saveScriptState.value = UiState.Error(it.message.toString()) }
        }
    }
}