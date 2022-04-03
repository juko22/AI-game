package com.juris_g.replace.repository

import com.juris_g.replace.common.intListTostring
import com.juris_g.replace.repository.models.GamePieceDBModel
import com.juris_g.replace.repository.models.GamePieceModel
import com.juris_g.replace.ui.models.GamePieceUIModel

fun GamePieceModel.asGamePieceDBModel() = GamePieceDBModel(
    id = id,
    numbers = numbers.toString(),
    points = points,
    nextSteps = nextSteps.toString()
)
