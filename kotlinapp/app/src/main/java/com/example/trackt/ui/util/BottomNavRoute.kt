package com.example.trackt.ui.util

import com.example.trackt.R

sealed class BottomNavRoute(
    val route: String,
    val title: String,
    val icon: Int
){
    data object Travels : BottomNavRoute(
        route = "travels",
        title = "travels",
        icon = R.drawable.travels
    )

    data object Explore : BottomNavRoute(
        route = "explore",
        title = "explore",
        icon = R.drawable.explore
    )

    data object Profile : BottomNavRoute(
        route = "profile",
        title = "profile",
        icon = R.drawable.profile
    )
}
