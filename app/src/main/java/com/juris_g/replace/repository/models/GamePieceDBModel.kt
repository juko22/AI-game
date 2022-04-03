package com.juris_g.replace.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juris_g.replace.common.GAME_PIECES_TABLE
import java.lang.reflect.Type

@Entity(tableName = GAME_PIECES_TABLE)
data class GamePieceDBModel(
    @PrimaryKey
    val id: Int,
    val numbers: String,
    val points: Int,
    val nextSteps: String,
)
@ProvidedTypeConverter
object DataConverters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
