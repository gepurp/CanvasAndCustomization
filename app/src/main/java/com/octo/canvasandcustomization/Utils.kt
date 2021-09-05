package com.octo.canvasandcustomization

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

object Utils {
    fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val background = view.background

        if (background != null) {
            background.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)
        return bitmap
    }
}