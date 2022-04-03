package com.juris_g.replace.common

import com.juris_g.replace.repository.models.GamePieceModel
import com.juris_g.replace.ui.models.GamePieceUIModel

fun createGamePiece(
    id: Int,
    numbers: List<Int>,
    points: Int,
    isMaxLevel: Boolean
): GamePieceModel = GamePieceModel(
    id = id,
    numbers = numbers,
    points = points,
    nextSteps = mutableListOf(),
    isMaxLevel = isMaxLevel
)

fun createUIGamePeace(id: Int, number: Int) = GamePieceUIModel(
    id = id,
    number = number,
    isSelected = false
)