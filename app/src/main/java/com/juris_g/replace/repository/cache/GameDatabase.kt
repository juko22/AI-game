package com.juris_g.replace.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.juris_g.replace.repository.Dao.GameDao
import com.juris_g.replace.repository.models.DataConverters
import com.juris_g.replace.repository.models.GamePieceDBModel

@Database(entities = [GamePieceDBModel::class], version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}