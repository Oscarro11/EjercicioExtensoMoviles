package plat.rickandmorty.app.character.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import okio.IOException

class KtorCharacterApi (
    private val client: HttpClient
) : CharacterApi {

    override suspend fun getCharacters(): List<CharacterDTO> {
        val response = try {
            client.get("character")
        } catch (e: UnresolvedAddressException) {
            Log.w("API", "Request for characters couldn't be completed, switched to local data")
            return emptyList()
        } catch (e: IOException) {
            Log.w("API", "Network request failed")
            return emptyList()
        }

        if (response.status == HttpStatusCode.OK) {
            val body: CharactersResponseDTO = response.body()
            return body.results
        } else {
            Log.e("API", "Request for characters gave code: ${response.status}")
            return emptyList()
        }
    }
}