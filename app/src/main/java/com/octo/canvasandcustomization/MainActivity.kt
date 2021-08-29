package com.octo.canvasandcustomization

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.octo.canvasandcustomization.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibSelectBrushSize.setOnClickListener {
            showBrushSizeDialog()
        }
    }

    private fun showBrushSizeDialog() {
        settingUpDialog()
        dialog.show()
    }

    private fun settingUpDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_brush_size_selector)
        dialog.setTitle("Select Size: ")

        dialog.apply {
            findViewById<ImageButton>(R.id.ib_small_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(5f)
                    dialog.dismiss()
                }
            findViewById<ImageButton>(R.id.ib_medium_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(15f)
                    dialog.dismiss()
                }
            findViewById<ImageButton>(R.id.ib_large_brush)
                .setOnClickListener {
                    binding.dvDrawing.setBushSize(25f)
                    dialog.dismiss()
                }
        }
    }
}