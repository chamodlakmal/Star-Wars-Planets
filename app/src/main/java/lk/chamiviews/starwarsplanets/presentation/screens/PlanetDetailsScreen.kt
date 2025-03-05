package lk.chamiviews.starwarsplanets.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import lk.chamiviews.starwarsplanets.R
import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.presentation.components.CommonTopAppBar

@Composable
fun PlanetDetailsScreen(
    planet: Planet,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = planet.name, onClick = onBackPressed, showIcon = true)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/200/300")
                    .placeholder(R.drawable.ic_planet_placeholder)
                    .error(R.drawable.ic_planet_error)
                    .build(),
                contentDescription = "planet image",
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text("Orbital Period: ${planet.orbitalPeriod}")
            Text("Gravity: ${planet.gravity}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetDetailScreenPreview() {
    PlanetDetailsScreen(
        planet = Planet(
            name = "Earth",
            climate = "hot",
            orbitalPeriod = "43",
            gravity = "1 standard"
        ),
        onBackPressed = {}
    )
}
