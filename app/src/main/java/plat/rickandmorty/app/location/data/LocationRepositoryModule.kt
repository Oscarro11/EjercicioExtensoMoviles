package plat.rickandmorty.app.location.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationRepositoryModule {

    @Binds @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}