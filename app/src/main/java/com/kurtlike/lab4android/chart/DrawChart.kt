package com.kurtlike.lab4android.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.kurtlike.lab4android.R
import com.kurtlike.lab4android.datainput.Dot


class DrawChart(context: Context): View(context) {
    private var chartHeight: Int = 0
    private var chartWidth: Int = 0
    private var xMin: Int = 0
    private var yMin: Int = 0
    private var xMax: Int = 0
    private var yMax: Int = 0
    private var xNull: Int = 0
    private var yNull: Int = 0
    private val xScale = 100
    private val yScale = 100
    private var paint: Paint = Paint()
    private var path: Path = Path()
    private lateinit var dashPathEffect: DashPathEffect
    private var xIntervals = floatArrayOf(0.1f * xScale, 0.1f * xScale)
    private var yIntervals = floatArrayOf(0.1f * yScale, 0.1f * yScale)
    private var dots = ArrayList<Dot>()
    init{
        paint.color = Color.BLUE
        paint.strokeWidth = 5F
    }
    fun setSize(width: Int, height: Int){
        this.layoutParams = ViewGroup.LayoutParams(width, height)
        chartHeight = height
        chartWidth = width
        xMax = width
        yMax = height
        xNull = width/2
        yNull = height/2
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.GREEN)
        createXAxis(canvas)
        createYAxis(canvas)
        addDots(dots,canvas)
    }

    fun createXAxis(canvas: Canvas){
        setNormalLinePreset()
        canvas.drawLine(xMin.toFloat(), yNull.toFloat(), xMax.toFloat(), yNull.toFloat(), paint)
        val scale = xScale

        val numberOfHatchsLessNull = (xNull - xMin)/(scale)
        for(i in 0..numberOfHatchsLessNull ){
            setDottedLinePreset()
            path.reset()
            path.moveTo((xNull - i * scale).toFloat(), yMax.toFloat())
            path.lineTo((xNull - i * scale).toFloat(), yMin.toFloat())
            canvas.drawPath(path, paint)
            setTextlLinePreset()
            canvas.drawText(( - i).toString(), (xNull - i * scale).toFloat() - 20, yNull.toFloat() + 20, paint)
        }

        val numberOfHatchsMoreNull = (xMax - xNull)/(scale)
        for(i in 1..numberOfHatchsMoreNull ){
            setDottedLinePreset()
            path.reset()
            path.moveTo((xNull + i * scale).toFloat(), yMax.toFloat())
            path.lineTo((xNull + i * scale).toFloat(), yMin.toFloat())
            canvas.drawPath(path, paint)
            setTextlLinePreset()
            canvas.drawText(i.toString(), (xNull + i * scale).toFloat() - 20, yNull.toFloat() + 20, paint)
        }

    }
    fun createYAxis(canvas: Canvas){
        setNormalLinePreset()
        canvas.drawLine(xNull.toFloat(), yMax.toFloat(), xNull.toFloat(), yMin.toFloat(), paint)
        val scale = yScale

        val numberOfHatchsLessNull = (yNull - yMin)/(scale)
        for(i in 0..numberOfHatchsLessNull ){
            setDottedLinePreset()
            path.reset()
            path.moveTo((xMin).toFloat(), (yNull - i * scale).toFloat())
            path.lineTo((xMax).toFloat(), (yNull - i * scale).toFloat())
            canvas.drawPath(path, paint)
            setTextlLinePreset()
            canvas.drawText(( - i).toString(), (xNull).toFloat() - 20, (yNull + i * scale).toFloat() + 20, paint)
        }

        val numberOfHatchsMoreNull = (yMax - yNull)/(scale)
        for(i in 1..numberOfHatchsMoreNull ){
            setDottedLinePreset()
            path.reset()
            path.moveTo((xMin).toFloat(), (yNull + i * scale).toFloat())
            path.lineTo((xMax).toFloat(), (yNull + i * scale).toFloat())
            canvas.drawPath(path, paint)
            setTextlLinePreset()
            canvas.drawText(i.toString(), (xNull).toFloat() - 20, (yNull - i * scale).toFloat() + 20, paint)

        }

    }
    fun setDottedLinePreset(){
        xIntervals = floatArrayOf(0.1f * xScale, 0.1f * xScale)
        yIntervals = floatArrayOf(0.1f * yScale, 0.1f * yScale)

        dashPathEffect = DashPathEffect(xIntervals, 0F)
        paint.pathEffect = dashPathEffect
        paint.strokeWidth = 3F
        paint.color = Color.argb(100,0,0,255)
        paint.style = Paint.Style.STROKE
    }
    fun setNormalLinePreset(){
        dashPathEffect = DashPathEffect(floatArrayOf(1F,0F), 0F)
        paint.pathEffect = dashPathEffect
        paint.strokeWidth = 5F
        paint.color = Color.argb(255,0,0,255)
        paint.style = Paint.Style.STROKE
    }
    fun setTextlLinePreset(){
        dashPathEffect = DashPathEffect(floatArrayOf(1F,0F), 0F)
        paint.pathEffect = dashPathEffect
        paint.strokeWidth = 3F
        paint.textSize = 20F
        paint.color = Color.argb(255,0,0,255)
        paint.style = Paint.Style.STROKE
    }
    fun addDots(dots:ArrayList<Dot>, canvas: Canvas){
        paint.isAntiAlias = true
        paint.strokeWidth = 20F
        paint.color = Color.argb(255,255,0,0)
        paint.style = Paint.Style.STROKE
        dots.forEach {
            canvas.drawPoint(xNull + xScale * it.x.toFloat() ,yNull - yScale * it.y.toFloat(),paint)
        }
    }
    fun setDots(dots:ArrayList<Dot>){
        this.dots = dots
    }
    @SuppressLint("ClickableViewAccessibility")
    fun moveChartListener(){
        this.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN ->{
                    println("$x $y")
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }


}