package com.octo.canvasandcustomization

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
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
    private var color = Color.GRAY
    private val paths = ArrayList<CustomPath>()
    private val undoPaths = ArrayList<CustomPath>()

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
        // TODO: Delete later
        brushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)

        for (p in paths) {
            drawPaint!!.strokeWidth = p.brushThickness
            drawPaint!!.color = p.color
            canvas.drawPath(p, drawPaint!!)
        }

        if (!drawPath!!.isEmpty) {
            drawPaint!!.strokeWidth = drawPath!!.brushThickness
            drawPaint!!.color = drawPath!!.color
            canvas.drawPath(drawPath!!, drawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath!!.color = color
                drawPath!!.brushThickness = brushSize
                drawPath!!.reset()
                drawPath!!.moveTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {
                drawPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP -> {
                paths.add(drawPath!!)
                drawPath = CustomPath(color, brushSize)
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun setBushSize(s: Float) {
        brushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            s,
            resources.displayMetrics
        )
        drawPaint!!.strokeWidth = brushSize
    }

    fun setBrushColor(c: Int) {
        color = c
    }

    fun undo() {
        if (paths.size > 0) {
            undoPaths.add(paths.removeAt(paths.size - 1))
            invalidate()
        }
    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path()
}


