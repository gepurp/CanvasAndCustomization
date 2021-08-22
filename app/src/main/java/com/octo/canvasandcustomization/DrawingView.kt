package com.octo.canvasandcustomization

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DrawingView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {

    private var canvas: Canvas? = null
    private var canvasBitmap: Bitmap? = null
    private var canvasPaint: Paint? = null
    private var drawPath: CustomPath? = null
    private var drawPaint: Paint? = null
    private var brushSize: Float = 0.toFloat()
    private var color = Color.BLACK

    init {
        initDrawing()
    }

    private fun initDrawing() {
        drawPaint = Paint().apply {
            color = this@DrawingView.color
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        drawPath = CustomPath(color, brushSize)
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20.toFloat()
    }

    internal inner class CustomPath(
        var color: Int,
        var brushThickness: Float
    ) : Path() {

    }
}


