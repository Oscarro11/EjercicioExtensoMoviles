package plat.rickandmorty.app.location.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import okio.IOException

class KtorLocationApi (
    private val client: HttpClient
) : LocationApi {

    override suspend fun getLocations(): List<LocationDTO> {
        val response = try {
            client.get("location")
        } catch (e: UnresolvedAddressException) {
            Log.w("API", "Request for locations couldn't be completed, switched to local data")
            return emptyList()
        } catch (e: IOException) {
            Log.w("API", "Network request failed")
            return emptyList()
        }

        if (response.status == HttpStatusCode.OK) {
            val body: LocationsResponseDTO = response.body()
            return body.results

        } else {
            Log.e("API", "Request for locations gave code: ${response.status}")
            return emptyList()
        }
    }
}