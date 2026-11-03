package plat.rickandmorty.app.location.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import plat.rickandmorty.app.core.data.AppDatabase
import plat.rickandmorty.app.core.network.HttpClientFactory
import plat.rickandmorty.app.location.data.local.LocationDAO
import plat.rickandmorty.app.location.data.remote.KtorLocationApi
import plat.rickandmorty.app.location.data.remote.LocationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationDataModule {

    @Provides @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "uvg.db"
        ).build()

    @Provides
    fun provideLocationDao(database: AppDatabase): LocationDAO =
        database.locationDAO()

    @Provides @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClientFactory.create()

    @Provides @Singleton
    fun provideLocationApi(client: HttpClient): LocationApi =
        KtorLocationApi(client)
}