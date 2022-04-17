package com.juris_g.replace.repository

import com.juris_g.replace.repository.models.GamePieceDBModel
import com.juris_g.replace.repository.models.GamePieceModel

fun GamePieceModel.asGamePieceDBModel() = GamePieceDBModel(
    id = id,
    numbers = numbers.toString(),
    points = points,
    nextSteps = nextSteps.toString()
)

fun List<GamePieceModel>.asGamePiecesDBModelList() = map {
    it.asGamePieceDBModel()
}
