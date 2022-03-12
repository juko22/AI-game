package com.juris_g.replace.repository

import com.juris_g.replace.common.createGamePiece
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.ui.models.GamePieceUIModel

interface GameRepository {
    fun startTheGame(): List<GamePieceUIModel>
}

class GameRepositoryImpl(private val gameDao: GameDao) : GameRepository {

    override fun startTheGame(): List<GamePieceUIModel> {
        val numbers = mutableListOf<GamePieceUIModel>()
        for (i in 0..11) {
            //numbers.add(createGamePiece((1..9).random()))
        }
        return numbers
    }
}