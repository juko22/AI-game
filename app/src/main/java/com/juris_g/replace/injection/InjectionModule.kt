package com.juris_g.replace.injection

import androidx.room.Room
import com.juris_g.replace.App
import com.juris_g.replace.common.DATABASE_NAME
import com.juris_g.replace.repository.*
import com.juris_g.replace.repository.cache.GameDatabase
import com.juris_g.replace.repository.models.DataConverters
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InjectionModule(private val app: App) {

    @Provides
    @Singleton
    fun provideGameDatabase(): GameDatabase = Room
        .databaseBuilder(app, GameDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideGameRepository(database: GameDatabase, gameTree: GameTreeManager): GameRepository =
        GameRepositoryImpl(database.gameDao(), gameTree)

    @Provides
    @Singleton
    fun provideMinMaxAlgorithm(database: GameDatabase): MiniMaxAlgorithm =
        MiniMaxAlgorithmImpl(database.gameDao())

    @Provides
    @Singleton
    fun provideGameTreeManager(database: GameDatabase, minMaxAlg: MiniMaxAlgorithm): GameTreeManager =
        GameTreeManagerImpl(database.gameDao(), minMaxAlg)
}