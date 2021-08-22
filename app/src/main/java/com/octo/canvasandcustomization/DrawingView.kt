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

    internal inner class CustomPath(
        var color: Int,
        var brushThickness: Float
    ) : Path() {

    }
}


