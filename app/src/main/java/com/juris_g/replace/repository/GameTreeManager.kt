package com.juris_g.replace.repository

import com.juris_g.replace.common.createGamePiece
import com.juris_g.replace.common.flowIO
import com.juris_g.replace.common.launchIO
import com.juris_g.replace.repository.models.GamePieceModel
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import kotlinx.coroutines.flow.Flow

interface GameTreeManager {
    fun createGameTree(): Flow<GamePieceModel>
}

class GameTreeManagerImpl() : GameTreeManager{

    private val gameTree = mutableListOf<GamePieceModel>()

    override fun createGameTree(): Flow<GamePieceModel> = flowIO {
            var numbers = mutableListOf<Int>()
            for (i in 0..4) {
                numbers.add((1..7).random())
            }
            var currentState = createGamePiece(0, numbers, 0)
            gameTree.add(currentState)
            var nextId = 1
            Timber.d("Numbers first: $numbers")

            while (true) {
                for (i in 0 until numbers.size - 1) {
                    Timber.d("Next id: $nextId")
                    val summ = numbers[i] + numbers[i + 1]
                    if (summ > 7) {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("New numbers list: $newNumbersList")
                        Timber.d("Iteration: $i")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 1)
                        val newPoints = currentState.points + 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
                    } else if (summ < 7) {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("Iteration: $i")
                        Timber.d("New numbers list: $newNumbersList")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 3)
                        val newPoints = currentState.points - 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
                    } else if (summ == 7) {
                        val newNumbersList = numbers.toMutableList()
                        Timber.d("New numbers list: $newNumbersList")
                        Timber.d("Iteration: $i")
                        newNumbersList.removeAt(i + 1)
                        newNumbersList.removeAt(i)
                        newNumbersList.add(i, 2  )
                        val newPoints = currentState.points + 1
                        gameTree.add(createGamePiece(nextId, newNumbersList, newPoints))
                        gameTree.find { it.id == currentState.id }?.nextSteps?.add(nextId)
                        nextId++
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
        emit(gameTree.first())
    }
}