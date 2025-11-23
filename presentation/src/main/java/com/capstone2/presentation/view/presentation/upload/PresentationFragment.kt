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
import com.capstone2.domain.model.audio.RequestAudioFile
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
    private val REQUEST_PERMISSION = 1001
    private val sessionViewModel : SessionViewModel by viewModels()
    private val audioUploadViewModel: AudioUploadViewModel by viewModels()

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


        // 🔹 새 버튼 클릭 시 오디오 선택
        binding.btnUpload.setOnClickListener {
            checkPermissionAndShowAudio()
        }

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
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_AUDIO), REQUEST_PERMISSION)
            } else {
                showAudioFiles()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6~12
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION)
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
        } ?: return

        val fileNames = audioFiles.map { it.name }.toTypedArray()
        AlertDialog.Builder(requireContext())
            .setTitle("Select Audio")
            .setItems(fileNames) { _, which ->
                val selectedFile = audioFiles[which]
                binding.tvUploadTitle.text = selectedFile.name

                val sessionState = sessionViewModel.sessionState.value
                if (sessionState is UiState.Success) {
                    val sessionId = sessionState.data.sessionId
                    audioUploadViewModel.upload(selectedFile, sessionId)
                } else {
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
                    LoggerUtil.d("sessionId: ${it.data.sessionId}")
                }
                is UiState.Error -> {
                    showToast("세션 생성에 실패했습니다.")
                }
            }
        }

        audioUploadViewModel.requestState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("uploadUrl: ${it.data.uploadUrl}")
                }
                is UiState.Error -> {
                    showToast("업로드 요청을 실패했습니다.")
                }
            }
        }

        audioUploadViewModel.uploadState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("data: ${it.data}")
                }
                is UiState.Error -> {
                    showToast("음성 파일 업로드에 실패했습니다.")
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
                Toast.makeText(requireContext(), "Storage permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
