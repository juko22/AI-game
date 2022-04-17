package com.juris_g.replace.common

import com.juris_g.replace.ui.models.GamePieceUIModel

fun List<GamePieceUIModel>.listToString(): String {
    var numbers = ""
    this.forEach { number ->
        numbers += " ${number.number}"
    }
    return numbers
}