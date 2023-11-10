package com.example.trackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackt.ui.screens.GoalsScreen
import com.example.trackt.ui.screens.ProfileScreen
import com.example.trackt.ui.screens.TravelsScreen
import com.example.trackt.ui.util.BottomNavRoute

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = BottomNavRoute.Travels.route,
        startDestination = BottomNavRoute.Travels.route
    ) {
        composable(route = BottomNavRoute.Travels.route) {
            TravelsScreen()
        }
        composable(route = BottomNavRoute.Goals.route) {
            GoalsScreen()
        }
        composable( route = BottomNavRoute.Profile.route,
        ) {
            ProfileScreen()
        }
    }
}