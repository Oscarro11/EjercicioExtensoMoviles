package plat.rickandmorty.app.location.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Query("SELECT * FROM locations")
    fun getLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocationById(id: Int): Flow<LocationEntity>

    @Insert
    suspend fun insertLocation(vararg locations: LocationEntity)

    @Query("DELETE FROM locations")
    suspend fun deleteLocations()

    @Delete
    suspend fun deleteLocation(location: LocationEntity)
}