package com.juris_g.replace.ui

import androidx.lifecycle.ViewModel
import com.juris_g.replace.repository.GameRepository
import com.juris_g.replace.ui.models.GamePieceUIModel

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    val gamePieces = repository.gamePieces

    fun startGame() = repository.startTheGame()

    fun gamePieceClicked(gamePiece: GamePieceUIModel) = repository.gamePieceClicked(gamePiece)
}