package plat.rickandmorty.app.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    label: String,
    data: String,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = data,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IconWithBackground(
    @DrawableRes iconId: Int,
    size: Dp,
    backgroundColor: Color,
    backgroundShape: Shape,
    tintColor: Color?,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 0.dp,
    iconScale: Float = 0.5f,
    content: @Composable (RowScope.() -> Unit)? = null
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = size,
                    minHeight = size
                )
                .background(
                    color = backgroundColor,
                    shape = backgroundShape
                )
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = tintColor ?: Color.Unspecified,
                    modifier = Modifier.size(size * iconScale)
                )

                if (content != null){
                    Spacer(modifier = Modifier.size(8.dp))

                    content()
                }
            }
        }
    }
}