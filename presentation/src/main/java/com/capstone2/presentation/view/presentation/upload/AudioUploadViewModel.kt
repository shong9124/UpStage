package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.repository.AudioRepository
import com.capstone2.domain.repository.TokenRepository
import com.capstone2.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AudioUploadViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _uploadState = MutableLiveData<UiState<Boolean>>()
    val uploadState: LiveData<UiState<Boolean>> get() = _uploadState

    fun finalizeUpload(
        file: File,
        sessionId: Int,
        // GetUploadUrlViewModel에서 받은 정보
        gcsUri: String,
        objectPath: String,
        uploadUrl: String
    ) {
        viewModelScope.launch {
            _uploadState.value = UiState.Loading

            val uploaderId: Int
            try {
                // Flow에서 실제 userId 가져오기
                val userIdPreferences = tokenRepository.getUserId().first()
                uploaderId = userIdPreferences.userId
            } catch (e: Exception) {
                _uploadState.value = UiState.Error(e.message ?: "Failed to get userId")
                return@launch
            }

            // 1. 🚨 수정된 부분: I/O 작업(파일 업로드)을 Dispatchers.IO로 이동하여 NetworkOnMainThreadException 방지
            val uploadResult = withContext(Dispatchers.IO) {
                audioRepository.uploadAudioToPresignedUrl(
                    uploadUrl,
                    file,
                    "audio/wav"
                )
            }

            uploadResult.onSuccess { uploadSuccess ->

                // 2. GCS 업로드 성공 후, 서버에 최종 파일 정보 요청 (RequestAudioFile)
                val request = RequestAudioFile(
                    sessionId = sessionId,
                    uploaderId = uploaderId,
                    gcsUri = gcsUri,
                    objectPath = objectPath, // GetUploadUrlResult의 objectName이 이 역할을 수행
                    contentType = "audio/wav",
                    sizeBytes = file.length().toInt()
                )

                audioRepository.requestAudioFile(request)
                    .onSuccess {
                        // RequestAudioFileResult 자체는 필요 없으므로 성공 상태만 전달
                        _uploadState.value = UiState.Success(uploadSuccess)
                    }
                    .onFailure { e ->
                        _uploadState.value = UiState.Error(e.message ?: "Server file registration failed")
                    }

            }.onFailure { e ->
                // 업로드 실패 시 에러 처리
                _uploadState.value = UiState.Error(e.message ?: "GCS Upload failed")
            }
        }
    }
}