package com.fauzimaulana.storyapp.view.upload

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.utils.Utils
import com.fauzimaulana.storyapp.core.vo.Resource
import com.fauzimaulana.storyapp.databinding.ActivityUploadStoryBinding
import com.fauzimaulana.storyapp.view.main.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class UploadStoryActivity : AppCompatActivity() {

    private var _binding: ActivityUploadStoryBinding? = null
    private val binding get() = _binding!!

    private val uploadStoryViewModel: UploadStoryViewModel by viewModel()

    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRES_PERMISSION, REQUEST_CODE_PERMISSION)
        }

        binding.buttonCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.buttonGallery.setOnClickListener {
            startGallery()
        }
        binding.buttonUpload.setOnClickListener {
            val storyDescription = binding.descriptionEditText.text
            if (storyDescription.toString().isEmpty()) {
                binding.descriptionEditTextLayout.error = "Please input your story description"
            } else {
                val alertDialogBuilder = AlertDialog.Builder(this)
                with(alertDialogBuilder) {
                    setTitle(resources.getString(R.string.alert))
                    setMessage(resources.getString(R.string.upload_confirmation))
                    setCancelable(false)
                    setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        uploadStoryViewModel.getUser().observe(this@UploadStoryActivity) { user ->
                            uploadStory(storyDescription.toString(), user.token)
                        }
                    }
                    setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRES_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!allPermissionGranted()) {
                Toast.makeText(this, "Did not get permission to access camera", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            getFile = myFile
            binding.previewImageView.setImageBitmap(result)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        Utils.createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadStoryActivity,
                "com.fauzimaulana.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = Utils.uriToFile(selectedImg, this@UploadStoryActivity)
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun uploadStory(description: String, token: String) {
        if (getFile != null) {
            val file = Utils.reduceFileImage(getFile as File)

            val storyDescription = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            uploadStoryViewModel.uploadStory(imageMultiPart, storyDescription, token).observe(this) { upload ->
                when (upload) {
                    is Resource.Loading -> {
                        binding.progressBar.root.visibility = View.VISIBLE
                        binding.contentUploadStory.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.root.visibility = View.GONE
                        Toast.makeText(this@UploadStoryActivity, "Your story uploaded", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UploadStoryActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    is Resource.Error -> {
                        binding.contentUploadStory.visibility = View.VISIBLE
                        binding.progressBar.root.visibility = View.GONE
                        Toast.makeText(this@UploadStoryActivity, "Failed to upload your story", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this@UploadStoryActivity, "Please choose a picture first.", Toast.LENGTH_LONG).show()
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(resources.getString(R.string.changed_lost))
            setMessage(resources.getString(R.string.confirmation))
            setCancelable(false)
            setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                finish()
            }
            setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog.cancel()}
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        showAlertDialog()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val REQUIRES_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }
}