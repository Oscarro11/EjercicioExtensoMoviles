package plat.rickandmorty.app.core.data

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.File
import javax.inject.Inject

class ProfileImageStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dir get() = File(context.filesDir, "profile_images").apply { mkdirs() }

    /** True only for temporary picker URIs that must be copied. */
    fun needsCopy(image: String) = image.startsWith("content://")

    /** True only for files this app created, so it never deletes remote URLs. */
    fun isOwned(image: String) =
        image.startsWith("file://") && image.toUri().path?.startsWith(dir.path) == true

    suspend fun copyIn(source: String): String = withContext(Dispatchers.IO) {
        val file = File(dir, "character_${System.currentTimeMillis()}.jpg")
        val input = context.contentResolver.openInputStream(source.toUri())
            ?: throw IOException("Cannot open $source")
        input.use { i -> file.outputStream().use { o -> i.copyTo(o) } }
        Uri.fromFile(file).toString()
    }

    suspend fun delete(image: String) = withContext(Dispatchers.IO) {
        if (isOwned(image)) image.toUri().path?.let { File(it).delete() }
    }
}
