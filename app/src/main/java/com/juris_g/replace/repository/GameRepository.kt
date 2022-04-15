package com.juris_g.replace.repository

import com.juris_g.replace.common.createMoveTurnModel
import com.juris_g.replace.common.createUIGamePeace
import com.juris_g.replace.common.launchIO
import com.juris_g.replace.common.listToString
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.GamePieceModel
import com.juris_g.replace.ui.models.GamePieceUIModel
import com.juris_g.replace.ui.models.GameTurnModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.single
import timber.log.Timber

interface GameRepository {
    val gamePieces: SharedFlow<List<GamePieceUIModel>>
    val gameMoves: SharedFlow<List<GameTurnModel>>
    var points: Int
    var wasPlayerFirst: Boolean
    fun startTheGame(playerFirst: Boolean)
    fun gamePieceClicked(gamePiece: GamePieceUIModel)
    fun makeTheMove()
    fun clearRepo()
}

class GameRepositoryImpl(
    private val gameDao: GameDao,
    private  val gameTreeManager: GameTreeManager
) : GameRepository {

    private val _gamePieces = MutableSharedFlow<List<GamePieceUIModel>>(replay = 1)
    private val _gameMoves = MutableSharedFlow<List<GameTurnModel>>(replay = 1)
    private var gamePiecesUI = mutableListOf<GamePieceUIModel>()
    private var gameTree = mutableListOf<GamePieceModel>()
    override var points = 0
    private lateinit var currentState: GamePieceModel
    override var wasPlayerFirst: Boolean = false
    private var lastID = 0
    private var movesList = mutableListOf<GameTurnModel>()
    private var moveTurnId = 0

    override val gamePieces = _gamePieces.asSharedFlow()
    override val gameMoves = _gameMoves.asSharedFlow()

    override fun startTheGame(playerFirst: Boolean) {
        launchIO {
            gamePiecesUI.clear()
            wasPlayerFirst = playerFirst
            points = 0
            val newNumbers = mutableListOf<GamePieceUIModel>()
            gameTree = gameTreeManager.createGameTree(playerFirst).single().toMutableList()
            val firstGamePiece = gameTree.first()
            currentState = firstGamePiece
            for (i in 0 until  firstGamePiece.numbers.size) {
                newNumbers.add(createUIGamePeace(i, firstGamePiece.numbers[i]))
            }
            gamePiecesUI = newNumbers
            Timber.d("UI list content at start: $gamePiecesUI")
            _gamePieces.tryEmit(newNumbers)
            lastID = newNumbers.last().id
            Timber.d("Is player first: $playerFirst")
            movesList.add(createMoveTurnModel(
                moveTurnId,
                "Start state",
                gamePiecesUI.listToString(),
                points.toString()
            ))
            _gameMoves.tryEmit(movesList)
            moveTurnId++
            if (!playerFirst) {
                makeComputerMove()
            }
        }
    }

    override fun gamePieceClicked(gamePiece: GamePieceUIModel) {
        launchIO {
            if (gamePiecesUI.filter { it.isSelected }.size == 1) {
                val selectedID = gamePiecesUI.first { it.isSelected }.id
                if (selectedID + 1 == gamePiece.id ||
                    selectedID - 1 == gamePiece.id ||
                    selectedID == gamePiece.id) {
                    val gamePieces = gamePiecesUI.map { it.copy() }
                    gamePieces.find { it.id == gamePiece.id }?.isSelected = !gamePiece.isSelected
                    gamePiecesUI = gamePieces.toMutableList()
                    _gamePieces.tryEmit(gamePieces)
                    Timber.d("Piece clicked changed")
                }
            } else if (gamePiecesUI.filter { it.isSelected }.size < 2 ||
                gamePiecesUI.first { it.id == gamePiece.id }.isSelected) {
                val gamePieces = gamePiecesUI.map { it.copy() }
                gamePieces.find { it.id == gamePiece.id }?.isSelected = !gamePiece.isSelected
                gamePiecesUI = gamePieces.toMutableList()
                _gamePieces.tryEmit(gamePieces)
                Timber.d("Piece clicked changed")
            }
        }
    }

    override fun makeTheMove() {
        launchIO {
            val selectedNumbers = gamePiecesUI.filter { it.isSelected }
            if (selectedNumbers.size < 2) return@launchIO
            val gamePieces = gamePiecesUI.map { it.copy() }.toMutableList()
            val sumOfSelectedNumbers = selectedNumbers[0].number + selectedNumbers[1].number
            when {
                sumOfSelectedNumbers > 7 -> {
                    points++
                    gamePieces.remove(selectedNumbers[0])
                    gamePieces[gamePieces.indexOf(selectedNumbers[1])] = createUIGamePeace(
                        selectedNumbers[1].id,
                        1
                    )
                    val possibleMoves = gameTree.filter { id ->
                        gameTree.first { it.id == currentState.id }.nextSteps.any { it == id.id }
                    }
                    currentState = possibleMoves.first {
                        it.points == points && areListsTheSame(it.numbers, gamePieces)
                    }
                    var nextID = 0
                    gamePiecesUI.clear()
                    currentState.numbers.forEach { number ->
                        nextID++
                        gamePiecesUI.add(createUIGamePeace(nextID, number))
                    }
                }
                sumOfSelectedNumbers < 7 -> {
                    points--
                    gamePieces.remove(selectedNumbers[0])
                    gamePieces[gamePieces.indexOf(selectedNumbers[1])] = createUIGamePeace(
                        selectedNumbers[1].id,
                        3
                    )
                    val possibleMoves = gameTree.filter { id ->
                        gameTree.first { it.id == currentState.id }.nextSteps.any { it == id.id }
                    }
                    currentState = possibleMoves.first { state ->
                        state.points == points && areListsTheSame(state.numbers, gamePieces)
                    }
                    var nextID = 0
                    gamePiecesUI.clear()
                    currentState.numbers.forEach { number ->
                        nextID++
                        gamePiecesUI.add(createUIGamePeace(nextID, number))
                    }
                }
                sumOfSelectedNumbers == 7 -> {
                    points++
                    gamePieces.remove(selectedNumbers[0])
                    gamePieces[gamePieces.indexOf(selectedNumbers[1])] = createUIGamePeace(
                        selectedNumbers[1].id,
                        2
                    )
                    val possibleMoves = gameTree.filter { id ->
                        gameTree.first {
                            it.id == currentState.id
                        }.nextSteps.any { it == id.id }
                    }
                    currentState = possibleMoves.first {
                        it.points == points && areListsTheSame(it.numbers, gamePieces)
                    }
                    var nextID = 0
                    gamePiecesUI.clear()
                    currentState.numbers.forEach { number ->
                        nextID++
                        gamePiecesUI.add(createUIGamePeace(nextID, number))
                    }
                }
            }
            _gamePieces.tryEmit(gamePiecesUI)
            movesList.add(createMoveTurnModel(
                moveTurnId,
                "Player $moveTurnId",
                gamePiecesUI.listToString(),
                points.toString()
            ))
            Timber.d("Move list: $movesList")
            _gameMoves.tryEmit(movesList)
            moveTurnId++
            Timber.d("Player move: $currentState")
            if (gamePiecesUI.size > 1) {
                makeComputerMove()
            }
        }
    }

    override fun clearRepo() {
        points = 0
        gameTree.clear()
        gamePiecesUI.clear()
        _gamePieces.tryEmit(gamePiecesUI)
        gameTreeManager.clearGameTree()
        lastID = 0
    }

    private fun areListsTheSame(list1: List<Int>, list2: List<GamePieceUIModel>): Boolean {
        list1.forEachIndexed { i, piece ->
            if (piece != list2[i].number) {
                return false
            }
        }
        return true
    }

    private fun makeComputerMove() {
        launchIO {
            if (wasPlayerFirst) {
                var computerMove = gameTree.firstOrNull { gameState ->
                    currentState.nextSteps.any { it == gameState.id } &&
                            gameState.gameResult == -1
                }
                if (computerMove == null) {
                    computerMove = gameTree.first { gameState ->
                        currentState.nextSteps.first() == gameState.id
                    }
                }
                Timber.d("Computer move: $computerMove")
                currentState = computerMove
                points = currentState.points
                var nextID = 0
                gamePiecesUI.clear()
                computerMove.numbers.forEach { number ->
                    nextID++
                    gamePiecesUI.add(createUIGamePeace(nextID, number))
                }
                movesList.add(createMoveTurnModel(
                    moveTurnId,
                    "Phone $moveTurnId",
                    gamePiecesUI.listToString(),
                    points.toString()
                ))
                _gameMoves.tryEmit(movesList)
                moveTurnId++
                _gamePieces.tryEmit(gamePiecesUI)
            } else {
                var computerMove = gameTree.firstOrNull { gameState ->
                    currentState.nextSteps.any { it == gameState.id } &&
                            gameState.gameResult == 1
                }
                if (computerMove == null) {
                    computerMove = gameTree.first { gameState ->
                        Timber.d("No state found with value 1")
                        currentState.nextSteps.first() == gameState.id
                    }
                }
                Timber.d("Computer move: $computerMove")
                currentState = computerMove
                points = currentState.points
                var nextID = 0
                gamePiecesUI.clear()
                computerMove.numbers.forEach { number ->
                    nextID++
                    gamePiecesUI.add(createUIGamePeace(nextID, number))
                }
                movesList.add(createMoveTurnModel(
                    moveTurnId,
                    "Phone $moveTurnId",
                    gamePiecesUI.listToString(),
                    points.toString()
                ))
                _gameMoves.tryEmit(movesList)
                moveTurnId++
                _gamePieces.tryEmit(gamePiecesUI)
            }
        }
    }
}