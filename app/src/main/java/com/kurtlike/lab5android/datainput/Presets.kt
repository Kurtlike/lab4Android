package com.kurtlike.lab5android.datainput

class Presets {
    val presets = ArrayList<Preset>()
    init {
        createPresets()
    }
    fun createPresets(){
        val preset1 = Preset()
        preset1.name = "Парабола"
        for (i in 1..13){
            val x = i - 7
            val y = Math.pow(x.toDouble(), 2.0)
            val dot = Dot(x.toDouble(), y)
            preset1.dots.add(dot)
        }
        presets.add(preset1)

        val preset2 = Preset()
        preset2.name = "Синусоида"
        for (i in 1..13){
            val x = i - 7
            val y = Math.sin(x.toDouble())
            val dot = Dot(x.toDouble(), y)
            preset2.dots.add(dot)
        }
        presets.add(preset2)

        val preset3 = Preset()
        preset3.name = "Линейная"
        for (i in 1..13){
            val x = i - 7
            val y = x.toDouble()
            val dot = Dot(x.toDouble(), y)
            preset3.dots.add(dot)
        }
        presets.add(preset3)
    }
    class Preset{
        var name = String()
        var dots = ArrayList<Dot>()
    }
}