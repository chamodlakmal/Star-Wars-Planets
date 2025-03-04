package lk.chamiviews.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lk.chamiviews.starwarsplanets.presentation.screens.PlanetsScreen
import lk.chamiviews.starwarsplanets.presentation.viewmodel.PlanetsViewModel
import lk.chamiviews.starwarsplanets.ui.theme.StarWarsPlanetsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val planetsViewModel: PlanetsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsPlanetsTheme {
                PlanetsScreen(planetsViewModel)
            }
        }
    }
}

