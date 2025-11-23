package com.capstone2.presentation.view.presentation.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.repository.AudioRepository
import com.capstone2.domain.repository.TokenRepository
import com.capstone2.domain.usecase.audio.UpdateDBStatusUseCase // 🚨 추가
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
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
    private val tokenRepository: TokenRepository,
    private val updateDBStatusUseCase: UpdateDBStatusUseCase // 🚨 추가
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

            // 1. I/O 작업(파일 업로드)을 Dispatchers.IO로 이동
            val uploadResult = withContext(Dispatchers.IO) {
                audioRepository.uploadAudioToPresignedUrl(
                    uploadUrl,
                    file,
                    "audio/wav"
                )
            }

            uploadResult.onSuccess { uploadSuccess ->

                // 2. 🚨 수정된 부분: GCS 업로드 성공 후, updateDBStatusUseCase를 호출하여 서버에 업로드 완료를 알림
                updateDBStatusUseCase.invoke(objectPath) // objectPath (GetUploadUrlResult의 objectName) 사용
                    .onSuccess {
                        // updateDBStatus 성공 시 최종 성공 처리
                        _uploadState.value = UiState.Success(uploadSuccess)
                        LoggerUtil.d("DB 업데이트 성공")
                    }
                    .onFailure { e ->
                        // DB 상태 업데이트 실패 시 에러 처리
                        _uploadState.value = UiState.Error(e.message ?: "Server file status update failed")
                    }

            }.onFailure { e ->
                // GCS 업로드 실패 시 에러 처리
                _uploadState.value = UiState.Error(e.message ?: "GCS Upload failed")
            }

            // NOTE: 기존 requestAudioFile 코드는 새로운 updateDBStatus 플로우를 따르기 위해 제거되었습니다.
        }
    }
}