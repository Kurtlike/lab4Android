package com.kurtlike.lab5android.localBuisness

import com.kurtlike.lab5android.datainput.Dot




interface GoodOldIO {
    fun setDotsForInterpolate(dots: ArrayList<Dot>)
    fun getXValue(xValue: Double): Double
    fun getDotsForDraw(): ArrayList<Dot>
    fun solve(): Boolean
}