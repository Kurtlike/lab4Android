package com.kurtlike.lab5android.localBuisness

import com.kurtlike.lab5android.datainput.Dot

class Test: GoodOldIO{
    var dots = ArrayList<Dot>()
    override fun setDotsForInterpolate(dots: ArrayList<Dot>) {
        this.dots = dots
    }

    override fun getXValue(xValue: Double): Double {
        return xValue - 1
    }

    override fun getDotsForDraw(): ArrayList<Dot> {
        dots.forEach {
            it.x--
            it.y--
        }
        return dots
    }

    override fun solve(): Boolean {
       return true
    }
}