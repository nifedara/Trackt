package com.example.trackt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.trackt.R
import com.example.trackt.TopBar
import com.example.trackt.ui.navigation.Graph
import com.example.trackt.ui.navigation.NavigationDestination
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.TracktGray1
import com.example.trackt.ui.theme.TracktPurple11
import com.example.trackt.ui.theme.TracktPurple2
import com.example.trackt.ui.theme.TracktPurple3
import com.example.trackt.ui.theme.TracktWhite1

object LoginScreen : NavigationDestination {
    override val route = "login"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = false,
                requiresLogo = true
            )
        },
        containerColor = TracktWhite1
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painterResource(id = R.drawable.traveldoodle),
                contentDescription = "travel doodle",
                contentScale = ContentScale.FillBounds,
                alpha = 0.14f,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp, 90.dp), horizontalArrangement = Arrangement.Start
                ) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.4.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardColors(containerColor = Color.White, disabledContainerColor = Color.White,
                            contentColor = Color.White, disabledContentColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.Center
                        ){
                            LoginForm {
                                navController.popBackStack()
                                navController.navigate(Graph.HOME)
                            }
                            LoginOther{
                                navController.popBackStack()
                                navController.navigate(SignupScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginForm( onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                    .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Welcome back",
                fontFamily = Caudex,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = TracktPurple11
            )
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
        ) {
            Column {
                Text(
                    text = "Email",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                TextField(value = "", onValueChange = {},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(50.dp),
                    colors = TextFieldDefaults.colors(focusedContainerColor = TracktPurple3, unfocusedContainerColor = TracktGray1,
                        cursorColor = TracktPurple2, focusedTextColor = TracktPurple11, unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
        ) {
            Column {
                Text(
                    text = "Password",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                TextField(value = "", onValueChange = {},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(50.dp),
                    colors = TextFieldDefaults.colors(focusedContainerColor = TracktPurple3, unfocusedContainerColor = TracktGray1,
                        cursorColor = TracktPurple2, focusedTextColor = TracktPurple11, unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot Password",
                fontFamily = Caudex,
                fontSize = 10.sp,
                textAlign = TextAlign.Right,
                color = Color.Black,
            )
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = onClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TracktPurple2,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign In",
                    fontFamily = Caudex,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun LoginOther( onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        ) {
            Divider(color = TracktPurple3)
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp), horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.size(240.dp, 40.dp),
                onClick = onClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TracktGray1
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.google), contentDescription = "Google Icon",
                    modifier = Modifier.size(14.dp, 14.dp), tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sign in with Google",
                    fontFamily = Caudex,
                    fontSize = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don’t have an account? Sign Up",
                fontFamily = Caudex,
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                color = TracktPurple11,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}

