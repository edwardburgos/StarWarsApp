package com.example.domain

enum class InformationSection {
    Eye_Color, Hair_Color, Skin_Color, Birth_Year; //TODO: enum items should be all in upper case, example EYE_COLOR

    override fun toString(): String { // TODO: if you are going to add function should use sealed class, but in this case you should use ENUM WITH PARAMETER see example on following coment:
        return super.toString().replace("_", " ")
    }
}

/* this is in the case you want to pass the string value from the enum
enum class InformationSectionExample(val value: String) {
    EYE_COLOR("eye colors")
}
Best implementation: get string res Id and get translatable string on the view.
enum class InformationSectionExample(@StringRes val value: Int) {
    EYE_COLOR("eye colors")
}*/