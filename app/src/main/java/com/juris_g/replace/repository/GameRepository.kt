package com.juris_g.replace.repository

import com.juris_g.replace.common.createUIGamePeace
import com.juris_g.replace.common.launchIO
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.GamePieceModel
import com.juris_g.replace.ui.models.GamePieceUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.single
import timber.log.Timber

interface GameRepository {
    val gamePieces: SharedFlow<List<GamePieceUIModel>>
    fun startTheGame(playerFirst: Boolean)
    fun gamePieceClicked(gamePiece: GamePieceUIModel)
}

class GameRepositoryImpl(
    private val gameDao: GameDao,
    private  val gameTreeManager: GameTreeManager
) : GameRepository {

    private val _gamePieces = MutableSharedFlow<List<GamePieceUIModel>>(replay = 1)
    private val gameUIPieces = mutableListOf<GamePieceUIModel>()
    private var gameTree = mutableListOf<GamePieceModel>()
    private var points = 0

    override val gamePieces: SharedFlow<List<GamePieceUIModel>> = _gamePieces.asSharedFlow()

    override fun startTheGame(playerFirst: Boolean) {
        launchIO {
            gameTree = gameTreeManager.createGameTree(playerFirst).single().toMutableList()
            val firstGamePiece = gameTree.first()
            for (i in 0 until  firstGamePiece.numbers.size) {
                gameUIPieces.add(createUIGamePeace(i, firstGamePiece.numbers[i]))
            }
            _gamePieces.tryEmit(gameUIPieces)
        }
    }

    override fun gamePieceClicked(gamePiece: GamePieceUIModel) {
        launchIO {
            gameUIPieces.find { it.id == gamePiece.id }?.isSelected = !gamePiece.isSelected
            _gamePieces.tryEmit(gameUIPieces)
            Timber.d("Piece clicked changed")
        }
    }
}