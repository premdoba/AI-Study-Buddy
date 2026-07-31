package com.nextgendevs.quicknotes.dependencyInjection

import android.content.Context
import com.nextgendevs.quicknotes.data.AppDatabase
import com.nextgendevs.quicknotes.data.local.QuizHistoryDao
import com.nextgendevs.quicknotes.data.local.TodoDao
import com.nextgendevs.quicknotes.data.remote.UsageRemoteDataSource
import com.nextgendevs.quicknotes.data.repository.AiRepositoryImpl
import com.nextgendevs.quicknotes.data.repository.QuizRepositoryImpl
import com.nextgendevs.quicknotes.data.repository.SettingsRepositoryImpl
import com.nextgendevs.quicknotes.data.repository.StudyRepositoryImpl
import com.nextgendevs.quicknotes.data.repository.TodoRepositoryImpl
import com.nextgendevs.quicknotes.data.repository.UsageRepositoryImpl
import com.nextgendevs.quicknotes.data.study.StudyDao
import com.nextgendevs.quicknotes.domain.repository.AiRepository
import com.nextgendevs.quicknotes.domain.repository.QuizRepository
import com.nextgendevs.quicknotes.domain.repository.SettingsRepository
import com.nextgendevs.quicknotes.domain.repository.StudyRepository
import com.nextgendevs.quicknotes.domain.repository.TodoRepository
import com.nextgendevs.quicknotes.domain.repository.UsageRepository
import com.nextgendevs.quicknotes.settings.SettingsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideStudyDao(db: AppDatabase) = db.studyDao()

    @Provides
    fun provideQuizDao(db: AppDatabase) = db.quizHistoryDao()

    @Provides
    @Singleton
    fun provideStudyRepo(dao: StudyDao): StudyRepository =
        StudyRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideQuizRepo(dao: QuizHistoryDao): QuizRepository =
        QuizRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideAiRepo(): AiRepository =
        AiRepositoryImpl()

    @Provides
    fun provideTodoDao(db: AppDatabase): TodoDao =
        db.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository =
        TodoRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideSettingsManager(
        @ApplicationContext context: Context
    ): SettingsManager {
        return SettingsManager(context)
    }

    @Provides
    @Singleton
    fun provideUsageRemoteDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): UsageRemoteDataSource {
        return UsageRemoteDataSource(firestore, auth)
    }

    @Provides
    @Singleton
    fun provideUsageRepository(
        remote: UsageRemoteDataSource
    ): UsageRepository =
        UsageRepositoryImpl(remote)


    @Provides
    @Singleton
    fun provideSettingsRepository(
        manager: SettingsManager
    ): SettingsRepository {
        return SettingsRepositoryImpl(manager)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()
}