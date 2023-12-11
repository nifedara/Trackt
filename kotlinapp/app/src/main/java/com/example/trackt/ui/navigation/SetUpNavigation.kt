package com.example.trackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trackt.TracktMainApp
import com.example.trackt.ui.screens.LoginScreen
import com.example.trackt.ui.screens.SignupScreen
import com.example.trackt.ui.screens.WelcomeScreen

//The App's Navigation
@Composable
fun SetUpNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen.route
    )
    {
        //welcome(onboard) screen
        composable(route = WelcomeScreen.route)
        {
            WelcomeScreen(navController = navController)
        }
        //signup screen
        composable(route = SignupScreen.route)
        {
            SignupScreen(navController = navController,
                         onUserSignUp = {
                             navController.popBackStack()
                             navController.navigate(LoginScreen.route)
                         })
        }
        //login screen
        composable(route = LoginScreen.route)
        {
            LoginScreen(onUserLogin = {
                        navController.popBackStack()
                        navController.navigate(Graph.HOME)},
                        navController = navController)
        }
        composable(route = Graph.HOME) {
            TracktMainApp()
        }
    }
}

object Graph {
    const val HOME = "home_graph"
    const val TRACKT = "trackt_graph"
}