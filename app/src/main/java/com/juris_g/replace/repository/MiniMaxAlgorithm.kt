package com.juris_g.replace.repository

import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.GamePieceModel
import timber.log.Timber

interface MiniMaxAlgorithm {
    fun findWinningPaths(gameStates: List<GamePieceModel>): List<GamePieceModel>
}

class MiniMaxAlgorithmImpl(gameDao: GameDao) : MiniMaxAlgorithm {

    override fun findWinningPaths(gameStates: List<GamePieceModel>) : List<GamePieceModel> {
        gameStates.forEach { gamePiece ->
            if (gamePiece.nextSteps.isEmpty() && (gamePiece.points + gamePiece.numbers[0]) % 2 == 0) {
                gamePiece.gameResult = 1
            } else if (gamePiece.nextSteps.isEmpty() && (gamePiece.points + gamePiece.numbers[0]) % 2 != 0) {
                gamePiece.gameResult = -1
            }
        }
        while (gameStates.any { it.gameResult == 0 }) {
            var currentResult: Int
            if (gameStates.last { it.gameResult == 0 }.isMaxLevel) {
                currentResult = -1
                gameStates.last { it.gameResult == 0 }.nextSteps.forEach { id ->
                    if (currentResult < gameStates.first { it.id == id }.gameResult) {
                        currentResult = gameStates.first { it.id == id }.gameResult
                    }
                }
                gameStates.last { it.gameResult == 0 }.gameResult = currentResult
            } else {
                currentResult = 1
                gameStates.last { it.gameResult == 0 }.nextSteps.forEach { id ->
                    if (currentResult > gameStates.first { it.id == id }.gameResult) {
                        currentResult = gameStates.first { it.id == id }.gameResult
                    }
                }
                gameStates.last { it.gameResult == 0 }.gameResult = currentResult
            }
        }
        return gameStates
    }
}