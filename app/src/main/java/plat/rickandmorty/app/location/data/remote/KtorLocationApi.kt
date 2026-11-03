package plat.rickandmorty.app.location.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class KtorLocationApi (
    private val client: HttpClient
) : LocationApi {

    override suspend fun getLocations(): List<LocationDTO> {
        val response = client.get("location")

        if (response.status == HttpStatusCode.OK) {
            val body: LocationsResponseDTO = response.body()
            return body.results

        } else {
            Log.e("API", "Request for locations had error: ${response.status}")
            return emptyList()
        }
    }
}