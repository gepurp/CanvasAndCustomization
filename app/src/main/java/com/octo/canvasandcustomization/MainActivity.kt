package com.octo.canvasandcustomization

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.octo.canvasandcustomization.databinding.ActivityMainBinding
import dev.sasikanth.colorsheet.ColorSheet
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var colors: IntArray

    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        colors = this.resources.getIntArray(R.array.rainbow)

        binding.apply {
            ibSelectBrushSize.setOnClickListener {
                showBrushSizeDialog()
            }
            ibSelectBrushColor.setOnClickListener {
                showColorPicker()
            }
            ibPickImageFromGallery.setOnClickListener {
                if (isStorageAccessAllowed()) {
                    pickImageFromGallery()
                } else {
                    requestStoragePermission()
                }
            }
            ibUndo.setOnClickListener {
                binding.dvDrawing.undo()
            }
        }
    }

    private fun showBrushSizeDialog() {
        Dialog(this).apply {
            setContentView(R.layout.dialog_brush_size_selector)
            setTitle("Select Size: ")
            findViewById<ImageButton>(R.id.ib_small_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(5f)
                    this.dismiss()
                }
            findViewById<ImageButton>(R.id.ib_medium_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(15f)
                    this.dismiss()
                }
            findViewById<ImageButton>(R.id.ib_large_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(25f)
                    this.dismiss()
                }
            show()
        }
    }

    private fun showColorPicker() {
        ColorSheet().colorPicker(
            colors = colors,
            listener = { color ->
                binding.dvDrawing.setBrushColor(color)
            })
            .show(supportFragmentManager)
    }

    private fun requestStoragePermission() {
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ).toString()
            )
        ) {
            Toast.makeText(
                this,
                "Need permission to add a Background",
                Toast.LENGTH_SHORT
            ).show()
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Now you can add background",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Oops... Now you cannot add background...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isStorageAccessAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun pickImageFromGallery() {
        val pickPhotoIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhotoIntent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                try {
                    if (data!!.data != null) {
                        binding.ivCanvasBackground.visibility = View.VISIBLE
                        binding.ivCanvasBackground.setImageURI(data.data)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error in parsing the image...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}