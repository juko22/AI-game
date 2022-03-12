package com.juris_g.replace.common

import com.juris_g.replace.ui.models.GamePieceUIModel

fun createGamePiece(number: Int): GamePieceUIModel = GamePieceUIModel(
    id = 0,
    number = number,
    isSelected = false
)