package com.juris_g.replace.repository.models

data class GamePieceModel(
    val id: Int,
    val numbers: List<Int>,
    val points: Int,
    val nextSteps: MutableList<Int>,
    var gameResult: Int = 0,
    var isMaxLevel: Boolean
)
