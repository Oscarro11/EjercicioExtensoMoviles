package plat.rickandmorty.app.character.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE id LIKE :id")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Insert
    suspend fun insertCharacter(vararg characters: CharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters")
    suspend fun deleteCharacters()

    @Query("UPDATE characters SET favorite = NOT favorite WHERE id = :id")
    suspend fun toggleCharacterFavorite(id: Int)
}