package lk.chamiviews.starwarsplanets.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.starwarsplanets.domain.model.Planet
import lk.chamiviews.starwarsplanets.presentation.components.CommonImageComponent
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
            CommonImageComponent(
                url = "https://picsum.photos/200/300",
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text("Orbital Period: ${planet.orbitalPeriod}")
            Text("Gravity: ${planet.gravity}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlanetDetailScreenPreview() {
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
