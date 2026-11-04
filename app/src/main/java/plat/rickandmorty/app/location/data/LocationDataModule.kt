package plat.rickandmorty.app.location.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import plat.rickandmorty.app.core.data.AppDatabase
import plat.rickandmorty.app.location.data.local.LocationDAO
import plat.rickandmorty.app.location.data.remote.KtorLocationApi
import plat.rickandmorty.app.location.data.remote.LocationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationDataModule {

    @Provides
    fun provideLocationDao(database: AppDatabase): LocationDAO =
        database.locationDAO()

    @Provides @Singleton
    fun provideLocationApi(client: HttpClient): LocationApi =
        KtorLocationApi(client)
}