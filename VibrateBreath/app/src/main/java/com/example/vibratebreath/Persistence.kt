package com.example.vibratebreath

class Persistence {

    fun findBreathTypes() : ArrayList<String> {
        val breathTypes = ArrayList<String>();

        // Tipos de Respiraciones
        breathTypes.add("Concentración");
        breathTypes.add("Activa");
        breathTypes.add("Calma");
        breathTypes.add("Consciencia");

        return breathTypes;
    }

    fun findBreaths() : ArrayList<String> {
        val breaths = ArrayList<String>();

        breaths.add("Respiración Diafragmática")
        breaths.add("Respiración Controlada")
        breaths.add("Respiración Cuadrada")
        breaths.add("Respiración Localizada")
        breaths.add("Respiración 4/7/8")

        return breaths;
    }

}