package com.juris_g.replace.ui

import androidx.lifecycle.ViewModel
import com.juris_g.replace.repository.GameRepository
import com.juris_g.replace.ui.models.GamePieceUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.properties.Delegates

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _playerHasWon = MutableSharedFlow<Boolean>(replay = 1)

    val playerHasWon = _playerHasWon.asSharedFlow()
    val gamePieces = repository.gamePieces
    val gameMoves = repository.gameMoves

    fun startGame(playerFirst: Boolean) {
        repository.startTheGame(playerFirst)
    }

    fun gamePieceClicked(gamePiece: GamePieceUIModel) = repository.gamePieceClicked(gamePiece)

    fun getPoints() = repository.points.toString()

    fun makeTheMove() = repository.makeTheMove()

    fun clearRepo() = repository.clearRepo()

    fun gameOver(number: Int) {
        val endPoints = repository.points + number
        if (endPoints % 2 == 0) {
            if (repository.wasPlayerFirst) {
                _playerHasWon.tryEmit(true)
            } else {
                _playerHasWon.tryEmit(false)
            }
        } else {
            if (!repository.wasPlayerFirst) {
                _playerHasWon.tryEmit(true)
            } else {
                _playerHasWon.tryEmit(false)
            }
        }
    }
}