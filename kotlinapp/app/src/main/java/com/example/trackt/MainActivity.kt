package com.example.trackt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.trackt.data.SplashViewModel
import com.example.trackt.ui.navigation.SetUpNavigationGraph
import com.example.trackt.ui.theme.TracktTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }

        setContent {
            TracktTheme {
                val screen by splashViewModel.startDestination
                SetUpNavigationGraph(navController = rememberNavController(), startDestination = screen)
//                Surface(// A surface container using the 'background' color from the theme
//                    modifier = Modifier.fillMaxSize(), color = White
//                ){
//                    val screen by splashViewModel.startDestination
//                    SetUpNavigationGraph(navController = rememberNavController(), startDestination = screen)
//                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TracktTheme { }
}