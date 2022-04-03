package com.juris_g.replace.repository

import com.juris_g.replace.common.createGamePiece
import com.juris_g.replace.common.flowIO
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.GamePieceModel
import timber.log.Timber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface GameTreeManager {
    fun createGameTree(playerFirst: Boolean): Flow<List<GamePieceModel>>
}

class GameTreeManagerImpl(
    private val gameDao: GameDao,
    private val minMaxAlg: MiniMaxAlgorithm
) : GameTreeManager{

    private val gameTree = mutableListOf<GamePieceModel>()

    override fun createGameTree(playerFirst: Boolean): Flow<List<GamePieceModel>> = flowIO {
        var numbers = mutableListOf<Int>()
        for (i in 0..5) {
            numbers.add((1..7).random())
        }
        var isMaxLevel = !playerFirst
        var currentState = createGamePiece(0, numbers, 0, isMaxLevel)
        gameTree.add(currentState)
        var nextId = 1
        Timber.d("Numbers first: $numbers")
        isMaxLevel = !isMaxLevel
        var numbersListSize = numbers.size - 1

        while (true) {
            for (i in 0 until numbers.size - 1) {
                Timber.d("Next id: $nextId")
                val sum = numbers[i] + numbers[i + 1]
                when {
                    sum > 7 -> {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("New numbers list: $newNumbersList")
                        Timber.d("Iteration: $i")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 1)
                        val newPoints = currentState.points + 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints, isMaxLevel))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
                    }
                    sum < 7 -> {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("Iteration: $i")
                        Timber.d("New numbers list: $newNumbersList")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 3)
                        val newPoints = currentState.points - 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints, isMaxLevel))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
                    }
                    sum == 7 -> {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("New numbers list: $newNumbersList")
                        Timber.d("Iteration: $i")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 2  )
                        val newPoints = currentState.points + 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints, isMaxLevel))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
                    }
                }
                if (numbersListSize > gameTree.last().numbers.size) {
                    isMaxLevel = !isMaxLevel
                    numbersListSize--
                    gameTree.last().isMaxLevel = isMaxLevel
                }
            }
            if (gameTree.any { it.id ==  currentState.id + 1}) {
                currentState = gameTree[currentState.id + 1]
                numbers = currentState.numbers.toMutableList()
            } else {
                break
            }
        }
        Timber.d("Game states: $gameTree")
        Timber.d("Game states with path: ${minMaxAlg.findWinningPaths(gameTree)}")
        emit(minMaxAlg.findWinningPaths(gameTree))
        //gameDao.addGameStates(gameTree)
    }
}