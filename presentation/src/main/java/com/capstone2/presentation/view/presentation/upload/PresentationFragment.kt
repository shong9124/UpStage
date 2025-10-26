package com.capstone2.presentation.view.presentation.upload

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone2.navigation.NavigationCommand
import com.capstone2.navigation.NavigationRoutes
import com.capstone2.presentation.R
import com.capstone2.presentation.base.BaseFragment
import com.capstone2.presentation.databinding.FragmentPresentationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class PresentationFragment : BaseFragment<FragmentPresentationBinding>() {

    private var timeJob: Job? = null
    private val REQUEST_PERMISSION = 1001

    override fun initView() {
        setBottomNav()

        val btnList = listOf(
            binding.btnPresentation,
            binding.btnInterview,
            binding.btnSpeech
        )

        for (btn in btnList) {
            btn.setOnClickListener {
                btnList.forEach { it.isSelected = it == btn }
                for (b in btnList) {
                    b.background = if (b.isSelected)
                        ContextCompat.getDrawable(requireContext(), R.drawable.btn_round_selected)
                    else
                        ContextCompat.getDrawable(requireContext(), R.drawable.btn_round)
                }
            }
        }

        // 🔹 새 버튼 클릭 시 오디오 선택
        binding.btnUpload.setOnClickListener {
            checkPermissionAndShowAudio()
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

    // 오디오 파일 목록 보여주고 선택 시 파일명 표시
    private fun showAudioFiles() {
        val audioDir = File(Environment.getExternalStorageDirectory(), "Download")
        if (!audioDir.exists()) {
            Toast.makeText(requireContext(), "Audio folder not found", Toast.LENGTH_SHORT).show()
            return
        }

        val audioFiles = audioDir.listFiles { file ->
            file.extension.lowercase() in listOf("mp3", "wav", "m4a")
        }

        if (audioFiles.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "No audio files found", Toast.LENGTH_SHORT).show()
            return
        }

        val fileNames = audioFiles.map { it.name }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Select Audio")
            .setItems(fileNames) { _, which ->
                val selectedFile = audioFiles[which]
                // 선택한 파일명 화면에 표시
                binding.tvUploadTitle.text = selectedFile.name
            }
            .show()
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
