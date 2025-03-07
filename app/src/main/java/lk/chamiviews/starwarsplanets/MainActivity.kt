package lk.chamiviews.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import lk.chamiviews.starwarsplanets.domain.model.Planet
import lk.chamiviews.starwarsplanets.presentation.route.args.PlanetDetailScreenArgs
import lk.chamiviews.starwarsplanets.presentation.route.args.PlanetListScreenArgs
import lk.chamiviews.starwarsplanets.presentation.route.args.SplashScreenArgs
import lk.chamiviews.starwarsplanets.presentation.screens.PlanetDetailsScreen
import lk.chamiviews.starwarsplanets.presentation.screens.PlanetsScreen
import lk.chamiviews.starwarsplanets.presentation.screens.SplashScreen
import lk.chamiviews.starwarsplanets.presentation.viewmodel.PlanetsViewModel
import lk.chamiviews.starwarsplanets.ui.theme.StarWarsPlanetsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsPlanetsTheme {
                val planetsViewModel: PlanetsViewModel = hiltViewModel()
                val navController = rememberNavController()
                val planetsState by planetsViewModel.planetsState.collectAsState()
                val isLoadingMore by planetsViewModel.isLoadingMore.collectAsState()
                NavHost(
                    navController = navController, startDestination = SplashScreenArgs
                ) {
                    composable<SplashScreenArgs> {
                        SplashScreen(onNavigateToPlanetsScreen = {
                            navController.navigate(PlanetListScreenArgs) {
                                popUpTo(SplashScreenArgs) { inclusive = true }
                            }
                        })
                    }
                    composable<PlanetListScreenArgs> {
                        PlanetsScreen(
                            planetsState = planetsState,
                            isLoadingMore = isLoadingMore,
                            onEvent = planetsViewModel::planetEvent,
                            navigateToPlanetDetails = { index, planet ->
                                navController.navigate(
                                    PlanetDetailScreenArgs(
                                        index = index,
                                        name = planet.name,
                                        orbitalPeriod = planet.orbitalPeriod,
                                        gravity = planet.gravity,
                                        climate = planet.climate
                                    )
                                )
                            }
                        )
                    }
                    composable<PlanetDetailScreenArgs> {
                        val args = it.toRoute<PlanetDetailScreenArgs>()
                        PlanetDetailsScreen(
                            planet = Planet(
                                index = args.index,
                                name = args.name,
                                orbitalPeriod = args.orbitalPeriod,
                                gravity = args.gravity,
                                climate = args.climate
                            ), onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

