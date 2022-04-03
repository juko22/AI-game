package com.juris_g.replace.common

import com.juris_g.replace.repository.models.GamePieceModel

fun List<Int>.intListTostring(): String {
    var numbers: String = ""
    this.forEach { number ->
        numbers += " $number"
    }
    return numbers
}