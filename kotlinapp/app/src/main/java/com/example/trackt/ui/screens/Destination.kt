package com.example.trackt.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackt.data.AppViewModelProvider
import com.example.trackt.data.TracktViewModel
import com.example.trackt.ui.navigation.NavigationDestination

object DestinationScreen : NavigationDestination {
    override val route = "destination"
}

@Composable
fun DestinationScreen(navController: NavHostController,
                viewModel: TracktViewModel = viewModel(factory = AppViewModelProvider.createViewModelInstance())
) {}