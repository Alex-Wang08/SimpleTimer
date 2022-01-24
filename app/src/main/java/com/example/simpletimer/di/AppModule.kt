package com.example.simpletimer.di

import android.app.Application
import androidx.room.Room
import com.example.simpletimer.MainViewModel
import com.example.simpletimer.data.TimerDatabase
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.data.TimerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * place to find how all the classes are created for injection
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTimerDatabase(app: Application): TimerDatabase {
        return Room.databaseBuilder(
            app,
            TimerDatabase::class.java,
            "timer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTimerRepository(db: TimerDatabase): TimerRepository {
        return TimerRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providesMainViewModel(): MainViewModel {
        return MainViewModel()
    }
}