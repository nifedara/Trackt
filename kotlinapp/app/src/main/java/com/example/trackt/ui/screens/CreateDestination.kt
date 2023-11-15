package com.example.trackt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.trackt.R
import com.example.trackt.TopBar
import com.example.trackt.ui.navigation.Graph
import com.example.trackt.ui.navigation.NavigationDestination
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.PurpleGrey40
import com.example.trackt.ui.theme.TracktPurple1
import com.example.trackt.ui.theme.TracktPurple11
import com.example.trackt.ui.theme.TracktPurple2
import com.example.trackt.ui.theme.TracktWhite2

object CreateDestinationScreen : NavigationDestination {
    override val route = "createDestination"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateDestinationScreen(navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = true,
                requiresLogo = false,
            ){navController.popBackStack()}
        },
        containerColor = TracktWhite2
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painterResource(id = R.drawable.doodle2),
                contentDescription = "travel doodle",
                contentScale = ContentScale.FillBounds,
                alpha = 0.1f,
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
                            CreateDestinationForm {
                                navController.popBackStack()
                                navController.navigate(Graph.HOME)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateDestinationForm( onClick: () -> Unit,
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
            Column {
                Text(
                    text = "Create a destination",
                    fontFamily = Caudex,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Have somewhere in mind to visit? \n" + "Add it to your Trackt bucket list.",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Left,
                    color = PurpleGrey40
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(300.dp, 50.dp)
                    .drawBehind {
                    drawRoundRect(color = TracktPurple2, cornerRadius = CornerRadius(8.dp.toPx()),
                        style = Stroke(1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)) )
                },
                border = BorderStroke(0.dp, Color.Transparent),
                shape = MaterialTheme.shapes.small) {
                Column {
                    Text(
                        text = "Upload destination image",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.img_icon),
                        tint = TracktPurple1,
                        modifier = Modifier.size(14.dp).align(Alignment.CenterHorizontally) ,
                        contentDescription = "img icon"
                    )
                }
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
        ) {
            Column {
                Text(
                    text = "Destination",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                OutlinedTextField(value = "", onValueChange = {},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(48.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TracktPurple2, unfocusedBorderColor = TracktPurple2,
                        cursorColor = TracktPurple1, focusedTextColor = TracktPurple11, unfocusedTextColor = Color.Black)
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
        ) {
            Column {
                Text(
                    text = "Budget",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                OutlinedTextField(value = "", onValueChange = {},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(48.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TracktPurple2, unfocusedBorderColor = TracktPurple2,
                        cursorColor = TracktPurple1, focusedTextColor = TracktPurple11, unfocusedTextColor = Color.Black)
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
        ) {
            Column {
                Text(
                    text = "Date",
                    fontFamily = Caudex,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
                    color = TracktPurple11
                )
                OutlinedTextField(value = "", onValueChange = {},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(48.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TracktPurple2, unfocusedBorderColor = TracktPurple2,
                        cursorColor = TracktPurple1, focusedTextColor = TracktPurple11, unfocusedTextColor = Color.Black)
                )
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TracktPurple2,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Create",
                    fontFamily = Caudex,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}