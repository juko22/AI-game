package com.juris_g.replace.injection

import androidx.room.Room
import com.juris_g.replace.App
import com.juris_g.replace.common.DATABASE_NAME
import com.juris_g.replace.repository.GameRepository
import com.juris_g.replace.repository.GameRepositoryImpl
import com.juris_g.replace.repository.GameTreeManager
import com.juris_g.replace.repository.GameTreeManagerImpl
import com.juris_g.replace.repository.cache.GameDatabase
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
    fun provideGameTreeManager(): GameTreeManager = GameTreeManagerImpl()
}