package com.juris_g.replace.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.GamePieceDBModel

@Database(entities = [GamePieceDBModel::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}