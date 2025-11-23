package com.capstone2.presentation.view.presentation.upload

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone2.domain.model.audio.GetUploadUrlRequest
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentPresentationBinding
import com.capstone2.presentation.util.UiState
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class PresentationFragment : BaseFragment<FragmentPresentationBinding>() {

    private var timeJob: Job? = null
    private var modelVersion = ""
    private var selectedAudioFile: File? = null // 선택된 오디오 파일을 저장할 변수
    private var currentSessionId: Int? = null // 현재 세션 ID를 저장할 변수

    private val REQUEST_PERMISSION = 1001
    private val sessionViewModel: SessionViewModel by viewModels()
    private val audioUploadViewModel: AudioUploadViewModel by viewModels()
    private val getUploadUrlViewModel: GetUploadUrlViewModel by viewModels()
    private val saveScriptViewModel: SaveScriptViewModel by viewModels()

    override fun initView() {
        setBottomNav()

        val btnList = listOf(
            binding.btnPresentation,
            binding.btnInterview,
            binding.btnSpeech
        )

        val buttonMap = mapOf(
            binding.btnPresentation to "PRESENTATION",
            binding.btnSpeech to "SPEECH",
            binding.btnInterview to "INTERVIEW"
        )

        for ((btn, version) in buttonMap) {
            btn.setOnClickListener {
                modelVersion = version

                buttonMap.keys.forEach { it.isSelected = it == btn }
                buttonMap.keys.forEach { b ->
                    b.background = if (b.isSelected)
                        ContextCompat.getDrawable(requireContext(), R.drawable.btn_round_selected)
                    else
                        ContextCompat.getDrawable(requireContext(), R.drawable.btn_round)
                }

                LoggerUtil.d("modelVersion changed to $modelVersion")
            }
        }

        // 대본 업로드 버튼
        binding.btnScript.setOnClickListener {
            val script = binding.etText.text.toString()
            currentSessionId?.let { id ->
                saveScriptViewModel.saveScript(
                    id,
                    SaveScript(
                        script, "KOREAN", true
                    )
                )
            }
        }

        // 🔹 새 버튼 클릭 시 오디오 선택
        binding.btnUpload.setOnClickListener {
            checkPermissionAndShowAudio()
        }

        // 세션 생성 버튼
        binding.btnSave.setOnClickListener {
            sessionViewModel.createSession(
                modelVersion,
                binding.etTitle.text.toString(),
            )
        }

        binding.btnSubmitP.setOnClickListener {
            var allFilled = true
            val editTextList = listOf(binding.etText, binding.etTitle)
            for (editText in editTextList) {
                if (editText.text.toString().trim().isEmpty()) {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke_error
                    )
                    allFilled = false
                } else {
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_edit_text_type_stroke
                    )
                }
            }
            if (!allFilled) {
                Toast.makeText(requireContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            moveToNext(NavigationRoutes.PresentationResult)
        }
    }

    // 권한 체크 후 오디오 파일 선택
    private fun checkPermissionAndShowAudio() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                    REQUEST_PERMISSION
                )
            } else {
                showAudioFiles()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6~12
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION
                )
            } else {
                showAudioFiles()
            }
        } else {
            showAudioFiles()
        }
    }

    // 파일 선택
    private fun showAudioFiles() {
        val audioDir = File(Environment.getExternalStorageDirectory(), "Download")
        val audioFiles = audioDir.listFiles { file ->
            file.extension.lowercase() in listOf("mp3", "wav", "m4a")
        } ?: run {
            Toast.makeText(requireContext(), "Download 폴더에 오디오 파일이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val fileNames = audioFiles.map { it.name }.toTypedArray()
        AlertDialog.Builder(requireContext())
            .setTitle("Select Audio")
            .setItems(fileNames) { _, which ->
                selectedAudioFile = audioFiles[which] // 파일 저장
                binding.tvUploadTitle.text = selectedAudioFile!!.name

                // 파일 선택 후, 세션 ID가 있는지 확인하고 업로드 URL 요청 시작
                currentSessionId?.let { sessionId ->
                    // 1. GetUploadUrl 요청을 위해 body 준비 (userId와 GetUploadUrl 모델의 sizeBytes 필드 반영)
                    val body = GetUploadUrlRequest(
                        sessionId = sessionId,
                        fileName = selectedAudioFile!!.name, // 서버가 GCS 경로 생성에 사용할 수 있도록 파일 이름 전달
                        contentType = "audio/wav",
                        sizeBytes = selectedAudioFile!!.length().toInt() // 파일 크기 추가
                    )
                    getUploadUrlViewModel.getUploadUrl(body)

                } ?: run {
                    Toast.makeText(requireContext(), "먼저 세션을 생성해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    override fun setObserver() {
        super.setObserver()

        sessionViewModel.sessionState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    currentSessionId = it.data.sessionId // 세션 ID 저장
                    LoggerUtil.d("sessionId: ${it.data.sessionId}")
                    // 세션이 성공적으로 생성되면, 이미 선택된 파일이 있을 경우 바로 업로드 URL 요청
                    if (selectedAudioFile != null) {
                        showToast("세션 생성 완료. 오디오 업로드 준비 중...")
                        val body = GetUploadUrlRequest(
                            sessionId = currentSessionId!!,
                            fileName = selectedAudioFile!!.name,
                            contentType = "audio/wav",
                            sizeBytes = selectedAudioFile!!.length().toInt() // 파일 크기 추가
                        )
                        getUploadUrlViewModel.getUploadUrl(body)
                    }
                }

                is UiState.Error -> {
                    showToast("세션 생성에 실패했습니다.")
                }
            }
        }

        saveScriptViewModel.saveScriptState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("대본 업로드에 성공했습니다.")
                }
                is UiState.Error -> {
                    showToast("대본 업로드에 실패했습니다.")
                }
            }
        }

        getUploadUrlViewModel.uploadUrlState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    // 🔹 GetUploadUrl 성공: Presigned URL, GCS 경로를 받음. -> 실제 업로드 시작
                    LoggerUtil.d("uploadUrl received: ${it.data.uploadUrl}")

                    val file = selectedAudioFile
                    val sessionId = currentSessionId
                    val result = it.data

                    if (file != null && sessionId != null) {
                        // GetUploadUrlResult의 objectName을 AudioUploadViewModel의 objectPath로 사용
                        audioUploadViewModel.finalizeUpload(
                            file = file,
                            sessionId = sessionId,
                            gcsUri = result.gcsUri,
                            objectPath = result.objectName,
                            uploadUrl = result.uploadUrl
                        )
                    } else {
                        showToast("업로드에 필요한 파일 또는 세션 ID가 준비되지 않았습니다.")
                    }
                }

                is UiState.Error -> {
                    showToast("업로드용 signed url 발급에 실패했습니다. (${it.message})")
                }
            }
        }

        // audioUploadViewModel.uploadState 관찰 (파일 업로드 및 서버 최종 등록 결과)
        audioUploadViewModel.uploadState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    showToast("파일 업로드 중...")
                }
                is UiState.Success -> {
                    LoggerUtil.d("File Upload and Registration Success: ${it.data}")
                    showToast("음성 파일 업로드 및 처리가 완료되었습니다.")
                }

                is UiState.Error -> {
                    LoggerUtil.e("Upload Error: ${it.message}")
                    showToast("음성 파일 업로드에 실패했습니다. (${it.message})")
                }
            }
        }
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(NavigationCommand.ToRouteAndClear(route))
        }
    }

    private fun setBottomNav() {
        binding.bottomNav.ivPresentation.setImageResource(R.drawable.ic_lamp_able)
        binding.bottomNav.tvPresentation.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.primary)
        )

        binding.bottomNav.menuBackstage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.ToRoute(NavigationRoutes.Home))
            }
        }

        binding.bottomNav.menuMyPage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.ToRoute(NavigationRoutes.MyPage))
            }
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAudioFiles()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Storage permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}