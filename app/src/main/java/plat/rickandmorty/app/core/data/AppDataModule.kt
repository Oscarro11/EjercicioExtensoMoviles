package plat.rickandmorty.app.core.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import plat.rickandmorty.app.core.network.HttpClientFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {

    @Provides @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "uvg.db"
        ).build()

    @Provides @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClientFactory.create()
}