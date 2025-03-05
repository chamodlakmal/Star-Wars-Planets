package lk.chamiviews.starwarsplanets.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import lk.chamiviews.starwarsplanets.domain.model.Planet
import lk.chamiviews.starwarsplanets.presentation.components.CommonTopAppBar
import lk.chamiviews.starwarsplanets.presentation.components.ErrorMessage
import lk.chamiviews.starwarsplanets.presentation.components.LoadingIndicator
import lk.chamiviews.starwarsplanets.presentation.components.PlanetItem
import lk.chamiviews.starwarsplanets.presentation.event.PlanetEvent
import lk.chamiviews.starwarsplanets.utils.LoadMoreStrategy
import lk.chamiviews.starwarsplanets.utils.LoadMoreStrategyImpl
import lk.chamiviews.starwarsplanets.presentation.state.PlanetsState

@Composable
fun PlanetsScreen(
    planetsState: PlanetsState,
    isLoadingMore: Boolean,
    loadMoreStrategy: LoadMoreStrategy = LoadMoreStrategyImpl(),
    onEvent: (PlanetEvent) -> Unit,
    navigateToPlanetDetails: (Planet) -> Unit
) {
    Scaffold(topBar = {
        CommonTopAppBar(
            title = "Star Wars Planet"
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (planetsState) {
                is PlanetsState.Loading -> LoadingIndicator()
                is PlanetsState.Success -> PlanetsList(
                    planets = (planetsState).planets,
                    onPlanetSelected = { planet: Planet -> navigateToPlanetDetails(planet) },
                    onEvent = onEvent,
                    isLoadingMore = isLoadingMore,
                    loadMoreStrategy = loadMoreStrategy
                )

                is PlanetsState.Error -> ErrorMessage(
                    message = (planetsState).errorMessage, onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun PlanetsList(
    planets: List<Planet>,
    onPlanetSelected: (Planet) -> Unit,
    onEvent: (PlanetEvent) -> Unit,
    isLoadingMore: Boolean,
    loadMoreStrategy: LoadMoreStrategy = LoadMoreStrategyImpl()
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
//            onEvent(PlanetEvent.LoadMorePlanets)
//        }
//    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }.collectLatest { layoutInfo ->
                val shouldLoadMore = loadMoreStrategy.shouldLoadMore(layoutInfo)
                if (shouldLoadMore) {
                    onEvent(PlanetEvent.LoadMorePlanets)
                }
            }
    }
}


@Composable
@Preview(showBackground = true)
private fun PlanetsScreenPreview() {
    PlanetsScreen(planetsState = PlanetsState.Success(
        planets = listOf(
            Planet(
                name = "Earth",
                climate = "Temperate",
                orbitalPeriod = "43",
                gravity = "1 standard"
            ), Planet(
                name = "Mars", climate = "Arid", orbitalPeriod = "43", gravity = "1 standard"
            ), Planet(
                name = "Jupiter",
                climate = "Gas Giant",
                orbitalPeriod = "43",
                gravity = "1 standard"
            )
        )
    ),
        isLoadingMore = false,
        loadMoreStrategy = LoadMoreStrategyImpl(),
        onEvent = {},
        navigateToPlanetDetails = {})
}
