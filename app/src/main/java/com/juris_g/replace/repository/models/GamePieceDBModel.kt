package com.juris_g.replace.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juris_g.replace.common.GAME_PIECES_TABLE

@Entity(tableName = GAME_PIECES_TABLE)
data class GamePieceDBModel(
    @PrimaryKey
    val id: Int,
    val number: Int,
    val isSelected: Boolean
)
