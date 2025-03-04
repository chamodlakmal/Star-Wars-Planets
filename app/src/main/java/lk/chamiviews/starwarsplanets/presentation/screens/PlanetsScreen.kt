package lk.chamiviews.starwarsplanets.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import lk.chamiviews.starwarsplanets.data.model.Planet
import lk.chamiviews.starwarsplanets.presentation.components.ErrorMessage
import lk.chamiviews.starwarsplanets.presentation.components.LoadingIndicator
import lk.chamiviews.starwarsplanets.presentation.loadmore.strategy.DefaultLoadMoreStrategy
import lk.chamiviews.starwarsplanets.presentation.loadmore.strategy.LoadMoreStrategy
import lk.chamiviews.starwarsplanets.presentation.state.PlanetsState
import lk.chamiviews.starwarsplanets.presentation.viewmodel.PlanetsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetsScreen(
    viewModel: PlanetsViewModel = hiltViewModel(),
    loadMoreStrategy: LoadMoreStrategy = DefaultLoadMoreStrategy()
) {
    val uiState by viewModel.planetsState.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val selectedPlanet by viewModel.selectedPlanet.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Star Wars Planet Viewer") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is PlanetsState.Loading -> LoadingIndicator()
                is PlanetsState.Success -> PlanetsList(
                    planets = (uiState as PlanetsState.Success).planets,
                    onPlanetSelected = viewModel::onPlanetSelected,
                    onLoadMore = viewModel::loadMorePlanets,
                    isLoadingMore = isLoadingMore,
                    loadMoreStrategy
                )

                is PlanetsState.Error -> ErrorMessage(
                    message = (uiState as PlanetsState.Error).errorMessage,
                    onRetry = viewModel::fetchPlanets
                )
            }
            if (selectedPlanet != null) {
                PlanetDetailsScreen(selectedPlanet!!, viewModel::onPlanetDetailsDismissed)
            }
        }
    }
}

@Composable
fun PlanetsList(
    planets: List<Planet>,
    onPlanetSelected: (Planet) -> Unit,
    onLoadMore: () -> Unit,
    isLoadingMore: Boolean,
    loadMoreStrategy: LoadMoreStrategy = DefaultLoadMoreStrategy()
) {

    // Remembers the lazy list state to track the scroll position and load more items when needed
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(planets) { planet ->
            PlanetItem(planet = planet, onPlanetSelected = onPlanetSelected)
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }

//    val shouldLoadMore by derivedStateOf {
//        loadMoreStrategy.shouldLoadMore(listState.layoutInfo)
//    }
//    LaunchedEffect(shouldLoadMore) {
//        if (shouldLoadMore) {
//            onLoadMore()
//        }
//    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collectLatest { layoutInfo ->
                val shouldLoadMore = loadMoreStrategy.shouldLoadMore(layoutInfo)
                if (shouldLoadMore) {
                    onLoadMore()
                }
            }
    }
}

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
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = planet.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Terrain: ${planet.climate}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PlanetsScreenPreview() {
    PlanetsScreen()
}