package com.octo.canvasandcustomization

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.octo.canvasandcustomization.databinding.ActivityMainBinding
import dev.sasikanth.colorsheet.ColorSheet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var colors: IntArray

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
}