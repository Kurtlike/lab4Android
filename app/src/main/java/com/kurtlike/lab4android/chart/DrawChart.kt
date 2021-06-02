package com.kurtlike.lab4android.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
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
    private var xScale = 100
    private var yScale = 100
    private var paint: Paint = Paint()
    private var path: Path = Path()
    private lateinit var dashPathEffect: DashPathEffect
    private var xIntervals = floatArrayOf(0.1f * xScale, 0.1f * xScale)
    private var yIntervals = floatArrayOf(0.1f * yScale, 0.1f * yScale)
    private var dots = ArrayList<Dot>()
    var xMoveStart = 0f
    var yMoveStart = 0f
    var xUpdateStart =0f
    var yUpdateStart =0f
    //var inTouch = false
    //var isZoom = false
    //var xZoomStart = 0f
    //var yZoomStart = 0f
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
        moveChartListener()
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
            canvas.drawText(( - i).toString(), (xNull).toFloat() - 20, (yNull - i * scale).toFloat() + 20, paint)
        }

        val numberOfHatchsMoreNull = (yMax - yNull)/(scale)
        for(i in 1..numberOfHatchsMoreNull ){
            setDottedLinePreset()
            path.reset()
            path.moveTo((xMin).toFloat(), (yNull + i * scale).toFloat())
            path.lineTo((xMax).toFloat(), (yNull + i * scale).toFloat())
            canvas.drawPath(path, paint)
            setTextlLinePreset()
            canvas.drawText(i.toString(), (xNull).toFloat() - 20, (yNull + i * scale).toFloat() + 20, paint)

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
            val actionMask = event.actionMasked
            val pointerIndex = event.actionIndex
            val pointerCount = event.pointerCount

            when (actionMask) {
                MotionEvent.ACTION_DOWN -> {
                    xMoveStart = event.x
                    xUpdateStart = event.x
                    yMoveStart = event.y
                    yUpdateStart = event.y
                    println("$event.x $event.y")
                    //inTouch = true
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    if(xMoveStart == event.x && yMoveStart == event.y) {
                        savedot(((event.x - xNull)/xScale).toDouble(), ((event.y - yNull)/-yScale).toDouble())
                        this.invalidate()
                    }

                    //inTouch = false
                    return@setOnTouchListener true
                }
                //MotionEvent.ACTION_POINTER_DOWN->{
                //    xZoomStart = event.x
                //    xZoomStart = event.y
                //    isZoom = true
                //    return@setOnTouchListener true
                //}
                MotionEvent.ACTION_MOVE ->{
                    //it doesn't work now ((
                    //if(isZoom){
                    //    val oldXSize = xMoveStart - xZoomStart
                    //    val newXSize =event.getX(0) - event.getX(1)
                    //    val oldYSize = yMoveStart - yZoomStart
                    //    val newYSize =event.getY(0) - event.getY(1)
//
                    //    xScale = (100*(newXSize/oldXSize)).toInt()
                    //    println(xScale)
                    //    yScale = (100*(newYSize/oldYSize)).toInt()
                    //    isZoom = false
                    //    this.invalidate()
                    //}
                    //else {
                    xNull += (-xUpdateStart + event.x).toInt()
                    yNull += (-yUpdateStart + event.y).toInt()
                    xUpdateStart = event.x
                    yUpdateStart = event.y
                        this.invalidate()
                        return@setOnTouchListener true
                    //}
                }
            }

            v?.onTouchEvent(event) ?: true
        }
    }
    fun savedot(x: Double, y:Double){
        val preferences =  context.getSharedPreferences("Dots", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val gson = Gson()
        dots.add(Dot(-1,x.toDouble(),y.toDouble()))
        editor.clear()
        val set = HashSet<String>()
        editor.clear()
        dots.forEach {
            val jString = gson.toJson(it)
            set.add(jString)
        }
        editor.putStringSet("dots", set)
        editor.commit()
    }


}