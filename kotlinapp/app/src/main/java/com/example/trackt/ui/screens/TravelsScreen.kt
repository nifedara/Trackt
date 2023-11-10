package com.example.trackt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackt.TopBar
import com.example.trackt.ui.navigation.NavigationDestination

object TravelsScreen : NavigationDestination {
    override val route = "travels"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TravelsScreen(
) {
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = false,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(0.dp, 90.dp), horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "My Destinations", color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row( modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, 100.dp), horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "A good day in Paris",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}