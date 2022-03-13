package com.juris_g.replace.ui.models

data class GamePieceUIModel(
    val id: Int,
    val number: Int,
    var isSelected: Boolean
) {
    fun getNumberAsString(): String = number.toString()
}
