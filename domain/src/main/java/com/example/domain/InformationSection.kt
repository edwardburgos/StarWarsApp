package com.example.domain

enum class InformationSection {
    Eye_Color, Hair_Color, Skin_Color, Birth_Year;

    override fun toString(): String {
        return super.toString().replace("_", " ")
    }}