package lk.chamiviews.starwarsplanets.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PlanetItem(
    name: String,
    climate: String
) {
    Card(
        modifier = Modifier
            .clickable {
                //TODO
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), colors = CardColors(
            containerColor = Color.White, contentColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ), elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(text = climate, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetItemPreview() {
    PlanetItem(name = "Earth", climate = "Temperate")
}