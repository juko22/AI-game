package com.juris_g.replace.repository.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.juris_g.replace.repository.models.GamePieceDBModel

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGameState(gameState: GamePieceDBModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGameStates(gameState: List<GamePieceDBModel>)

}