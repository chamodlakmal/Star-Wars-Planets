package lk.chamiviews.starwarsplanets.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun CommonImageComponent(modifier: Modifier = Modifier, url: String) {
    AsyncImage(
        model = url,
        contentDescription = "planet image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}