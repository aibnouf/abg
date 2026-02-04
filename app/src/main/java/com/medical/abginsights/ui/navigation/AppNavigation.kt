package com.medical.abginsights.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.medical.abginsights.ui.screens.AnalysisScreen
import com.medical.abginsights.ui.screens.HistoryScreen
import com.medical.abginsights.ui.screens.HomeScreen
import com.medical.abginsights.ui.screens.ResultsScreen
import com.medical.abginsights.viewmodel.AbgViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Analysis : Screen("analysis")
    object Results : Screen("results")
    object History : Screen("history")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: AbgViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartAnalysis = {
                    navController.navigate(Screen.Analysis.route)
                },
                onViewHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
        
        composable(Screen.Analysis.route) {
            AnalysisScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAnalysisComplete = {
                    navController.navigate(Screen.Results.route) {
                        popUpTo(Screen.Analysis.route) { inclusive = false }
                    }
                }
            )
        }
        
        composable(Screen.Results.route) {
            ResultsScreen(
                viewModel = viewModel,
                onNavigateHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNewAnalysis = {
                    viewModel.clearCurrentAnalysis()
                    navController.navigate(Screen.Analysis.route) {
                        popUpTo(Screen.Results.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAnalysisSelected = { analysisId ->
                    viewModel.loadAnalysis(analysisId)
                    navController.navigate(Screen.Results.route)
                }
            )
        }
    }
}
