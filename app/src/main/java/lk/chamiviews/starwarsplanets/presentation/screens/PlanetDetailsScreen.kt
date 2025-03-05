package lk.chamiviews.starwarsplanets.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.presentation.components.CommonTopAppBar

@Composable
fun PlanetDetailsScreen(
    planet: Planet,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = planet.name, onClick = onBackPressed)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Climate: ${planet.climate}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetDetailScreenPreview() {
    PlanetDetailsScreen(
        planet = Planet(name = "Earth", climate = "hot"),
        onBackPressed = {}
    )
}
