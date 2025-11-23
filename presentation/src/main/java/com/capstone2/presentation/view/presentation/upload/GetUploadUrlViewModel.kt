package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.audio.GetUploadUrl
import com.capstone2.domain.model.audio.GetUploadUrlRequest
import com.capstone2.domain.model.audio.GetUploadUrlResult
import com.capstone2.domain.usecase.audio.GetUploadUrlUseCase
import com.capstone2.domain.repository.TokenRepository
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUploadUrlViewModel @Inject constructor(
    private val getUploadUrlUseCase: GetUploadUrlUseCase,
    private val tokenRepository: TokenRepository // userId를 가져오기 위해 주입
) : ViewModel() {

    private val _uploadUrlState = MutableLiveData<UiState<GetUploadUrlResult>>()
    val uploadUrlState : LiveData<UiState<GetUploadUrlResult>> get() = _uploadUrlState

    fun getUploadUrl(request: GetUploadUrlRequest) {
        _uploadUrlState.value = UiState.Loading

        viewModelScope.launch {
            try {
                // 1. DataStore에서 userId를 비동기적으로 가져옵니다.
                val userIdPreferences = tokenRepository.getUserId().first()
                val userId = userIdPreferences.userId

                if (userId == 0) {
                    _uploadUrlState.value = UiState.Error("User ID is not available. Please log in again.")
                    return@launch
                }

                // 2. Fragment에서 받은 정보와 userId를 결합하여 최종 API 요청 모델을 만듭니다.
                val apiBody = GetUploadUrl(
                    contentType = request.contentType,
                    filename = request.fileName,
                    sessionId = request.sessionId,
                    sizeBytes = request.sizeBytes,
                    userId = userId // DataStore에서 가져온 userId 사용
                )

                // 3. UseCase를 호출하여 업로드 URL을 요청합니다.
                getUploadUrlUseCase.invoke(apiBody)
                    .onSuccess { _uploadUrlState.value = UiState.Success(it) }
                    .onFailure { _uploadUrlState.value = UiState.Error(it.message.toString()) }

            } catch (e: Exception) {
                _uploadUrlState.value = UiState.Error(e.message ?: "Failed to retrieve user ID for upload.")
            }
        }
    }
}