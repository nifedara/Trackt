package com.example.trackt

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trackt.ui.navigation.BottomNavGraph
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.PurpleGrey40
import com.example.trackt.ui.theme.TracktPurple1
import com.example.trackt.ui.theme.TracktPurple3
import com.example.trackt.ui.theme.TracktWhite1
import com.example.trackt.ui.theme.white20
import com.example.trackt.ui.util.BottomNavRoute

/**
 * Top level composable that represents main screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TracktMainApp() {
    val navController = rememberNavController()

    Scaffold( bottomBar = { BottomBar(navController = navController) })
    {
        BottomNavGraph(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    canNavigateBack: Boolean,
    requiresLogo: Boolean, //a workaround to include Trackt logo
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TracktWhite1,
                titleContentColor = TracktPurple1,
            ),
            title = { Text(text = "Go Back", style = MaterialTheme.typography.bodyMedium) },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        tint = TracktPurple1,
                        contentDescription = "back"
                    )
                }
            })
    }
    else if (requiresLogo){
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TracktWhite1),
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Trackt",
                    fontFamily = Caudex,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple1
                )
            }
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavRoute.Travels,
        BottomNavRoute.Goals,
        BottomNavRoute.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(containerColor = white20, tonalElevation = 20.dp) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavRoute,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title, style = MaterialTheme.typography.labelSmall) //TODO
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = TracktPurple1,
            selectedTextColor = TracktPurple1,
            unselectedIconColor = PurpleGrey40, //a lighter Trackt purple
            unselectedTextColor = PurpleGrey40, //a lighter Trackt purple
            indicatorColor = TracktPurple3
        ),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}