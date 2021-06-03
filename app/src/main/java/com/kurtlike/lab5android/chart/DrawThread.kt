package com.kurtlike.lab5android.chart

import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder

class DrawThread(_surfaceHolder: SurfaceHolder):Thread() {
    private var running = false
    private var surfaceHolder = _surfaceHolder


    fun setRunning(running: Boolean) {
        this.running = running
    }
    override fun run() {
        var canvas: Canvas?
        while (running) {
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas(null)
                if (canvas == null) continue
                canvas.drawColor(Color.GREEN)
            } finally {
                if (canvas != null) {
                    surfaceHolder!!.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

}