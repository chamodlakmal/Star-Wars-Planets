package lk.chamiviews.starwarsplanets.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.starwarsplanets.domain.model.Planet


@Composable
fun PlanetItem(
    planet: Planet,
    onPlanetSelected: (Planet) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onPlanetSelected(planet) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonImageComponent(
                url = "https://picsum.photos/640/480",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(
                text = planet.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Climate: ${planet.climate}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlanetItem() {
    PlanetItem(
        planet = Planet(
            name = "Earth",
            climate = "Hot",
            orbitalPeriod = "32",
            gravity = "1 standard"
        )
    ) { }
}