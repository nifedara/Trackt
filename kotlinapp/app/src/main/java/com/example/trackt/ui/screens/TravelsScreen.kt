package com.example.trackt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackt.R
import com.example.trackt.TopBar
import com.example.trackt.ui.navigation.Graph
import com.example.trackt.ui.navigation.NavigationDestination
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.PurpleGrey40
import com.example.trackt.ui.theme.TracktBlue1
import com.example.trackt.ui.theme.TracktGray1
import com.example.trackt.ui.theme.TracktPurple11
import com.example.trackt.ui.theme.TracktPurple3
import com.example.trackt.ui.theme.TracktWhite1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TravelsScreen(
) {
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = false,
                requiresLogo = false
            )
        },
        containerColor = TracktWhite1
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            ProfileMessage()
            Spacer(modifier = Modifier.height(30.dp))
            Destination(hasContent = true)
        }
    }
}

@Composable
fun ProfileMessage(
) {
    Column(modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "OLUWANIFEMI",
            fontFamily = Caudex,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            color = TracktBlue1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "It’s a bright day in Paris",
            fontFamily = Caudex,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            color = PurpleGrey40 //lighter version of TracktBlue1
        )
    }
}

@Composable
fun Destination( hasContent: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 1.4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(containerColor = Color.White, disabledContainerColor = Color.White,
                contentColor = Color.White, disabledContentColor = Color.White
            )
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)) {
                    Text(
                        text = "My Destination",
                        fontFamily = Caudex,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Left,
                        color = TracktPurple11
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.size(40.dp, 40.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = TracktPurple3,
                            contentColor = TracktPurple11)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "add (description) button",
                            modifier = Modifier.size(16.dp, 16.dp))
                    }
//                    Button(onClick = { /*TODO*/ },
//                        modifier = Modifier.size(40.dp, 40.dp),
//                        shape = MaterialTheme.shapes.small,
//                        colors = ButtonDefaults.buttonColors(containerColor = TracktPurple3,
//                            contentColor = TracktPurple11)) {
//                        Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "add (description) button",
//                            modifier = Modifier.size(20.dp, 20.dp))
//                    }
                }

                if (hasContent){
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                            Row(modifier = Modifier.weight(1f)) {
                                OutlinedCard {
                                    Image(painter = painterResource(id = R.drawable.img1), contentDescription = "destination image")
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(onClick = { /*TODO*/ },
                                        colors = ButtonDefaults.buttonColors(containerColor = TracktGray1)) {
                                        Text(text = "Ibadan",
                                            fontFamily = Caudex,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = TracktBlue1)
                                    }
                                }
                                OutlinedCard {
                                    Image(painter = painterResource(id = R.drawable.img2), contentDescription = "destination image")
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(onClick = { /*TODO*/ },
                                        colors = ButtonDefaults.buttonColors(containerColor = TracktGray1)) {
                                        Text(text = "Imo",
                                            fontFamily = Caudex,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = TracktBlue1)
                                    }
                                }
                            }
                            Row(modifier = Modifier.weight(1f)) {
                                OutlinedCard {
                                    Image(painter = painterResource(id = R.drawable.img13), contentDescription = "destination image")
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(onClick = { /*TODO*/ },
                                        colors = ButtonDefaults.buttonColors(containerColor = TracktGray1)) {
                                        Text(text = "Abuja",
                                            fontFamily = Caudex,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = TracktBlue1)
                                    }
                                }
                                OutlinedCard {
                                    Image(painter = painterResource(id = R.drawable.img4), contentDescription = "destination image")
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(onClick = { /*TODO*/ },
                                        colors = ButtonDefaults.buttonColors(containerColor = TracktGray1)) {
                                        Text(text = "Enugu",
                                            fontFamily = Caudex,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            color = TracktBlue1)
                                    }
                                }
                            }
                        }
                    }
                } else{
                    Spacer(modifier = Modifier.height(100.dp))
                    Image(
                        modifier = Modifier.align(CenterHorizontally) ,
                        painter = painterResource(id = R.drawable.travel_by_plane), 
                        contentDescription = "default image",
                    )
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}