package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import com.capstone2.domain.repository.AudioRepository
import com.capstone2.domain.repository.TokenRepository
import com.capstone2.domain.usecase.audio.AudioUploadUseCase
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AudioUploadViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _requestState = MutableLiveData<UiState<RequestAudioFileResult>>()
    val requestState: LiveData<UiState<RequestAudioFileResult>> get() = _requestState

    private val _uploadState = MutableLiveData<UiState<Boolean>>()
    val uploadState: LiveData<UiState<Boolean>> get() = _uploadState

    fun upload(file: File, sessionId: Int) {
        // 반드시 viewModelScope.launch 안에서 first() 호출
        viewModelScope.launch {
            try {
                // Flow에서 실제 userId 가져오기
                val userIdPreferences = tokenRepository.getUserId().first()
                val uploaderId = userIdPreferences.userId

                val request = RequestAudioFile(
                    sessionId = sessionId,
                    uploaderId = uploaderId,
                    gcsUri = "",      // 서버에서 채워줌
                    objectPath = "",  // 서버에서 채워줌
                    contentType = "audio/wav",
                    sizeBytes = file.length().toInt()
                )

                _requestState.value = UiState.Loading
                audioRepository.requestAudioFile(request)
                    .onSuccess { result ->
                        _requestState.value = UiState.Success(result)

                        // Presigned URL 받아오면 실제 파일 업로드
                        _uploadState.value = UiState.Loading
                        audioRepository.uploadAudioToPresignedUrl(
                            result.uploadUrl,
                            file,
                            request.contentType
                        ).onSuccess { success ->
                            _uploadState.value = UiState.Success(success)
                        }.onFailure { e ->
                            _uploadState.value = UiState.Error(e.message ?: "Upload failed")
                        }
                    }
                    .onFailure { e ->
                        _requestState.value = UiState.Error(e.message ?: "Request failed")
                    }
            } catch (e: Exception) {
                _requestState.value = UiState.Error(e.message ?: "Failed to get userId")
            }
        }
    }
}
