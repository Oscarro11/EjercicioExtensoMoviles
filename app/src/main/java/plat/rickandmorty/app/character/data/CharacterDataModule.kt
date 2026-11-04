package plat.rickandmorty.app.character.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import plat.rickandmorty.app.character.data.local.CharacterDAO
import plat.rickandmorty.app.character.data.remote.CharacterApi
import plat.rickandmorty.app.character.data.remote.KtorCharacterApi
import plat.rickandmorty.app.core.data.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDataModule {

    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDAO =
        database.characterDAO()

    @Provides @Singleton
    fun provideCharacterApi(client: HttpClient): CharacterApi =
        KtorCharacterApi(client)
}