package com.juris_g.replace.ui

import androidx.lifecycle.ViewModel
import com.juris_g.replace.repository.GameRepository

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    fun startGame() = repository.startTheGame()
}