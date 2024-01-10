package com.example.trackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.trackt.ui.screens.CreateDestinationScreen
import com.example.trackt.ui.screens.DestinationScreen
import com.example.trackt.ui.screens.ExploreScreen
import com.example.trackt.ui.screens.ProfileScreen
import com.example.trackt.ui.screens.TravelsScreen
import com.example.trackt.ui.util.BottomNavRoute

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavRoute.Travels.route
    ) {
        composable(route = BottomNavRoute.Travels.route) {
            TravelsScreen(addDestination = {navController.navigate(CreateDestinationScreen.route)},
                          navigateToDestination = {navController.navigate("${DestinationScreen.route}/$it")})
        }
        composable(route = BottomNavRoute.Explore.route) {
            ExploreScreen()
        }
        composable( route = BottomNavRoute.Profile.route,
        ) {
            ProfileScreen()
        }
        tracktNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.tracktNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.TRACKT,
        startDestination = CreateDestinationScreen.route
    ) {
        composable(route = CreateDestinationScreen.route) {
            CreateDestinationScreen(navController = navController)
        }
    }
}