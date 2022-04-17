package com.juris_g.replace.common

import com.juris_g.replace.repository.models.GamePieceModel
import com.juris_g.replace.ui.models.GamePieceUIModel
import com.juris_g.replace.ui.models.GameTurnModel

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

fun createMoveTurnModel(id: Int, turn: String, numbers: String, points: String) = GameTurnModel(
    id = id,
    turn = turn,
    numbers = numbers,
    points = points
)
