package plat.rickandmorty.app.core.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

fun darkenColor(originalColor: Color, factor: Float = 0.1f): Color {
    return Color.Black.copy(alpha = factor).compositeOver(background = originalColor)
}