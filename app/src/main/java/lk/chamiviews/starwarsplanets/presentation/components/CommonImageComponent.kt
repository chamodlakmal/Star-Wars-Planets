package lk.chamiviews.starwarsplanets.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import lk.chamiviews.starwarsplanets.R

@Composable
fun CommonImageComponent(modifier: Modifier = Modifier, url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(url)
            .placeholder(R.drawable.ic_planet_placeholder)
            .error(R.drawable.ic_planet_placeholder)
            .build(),
        contentDescription = "planet image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}