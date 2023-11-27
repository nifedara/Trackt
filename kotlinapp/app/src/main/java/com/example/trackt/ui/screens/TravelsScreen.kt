package com.example.trackt.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackt.R
import com.example.trackt.TopBar
import com.example.trackt.data.AppViewModelProvider
import com.example.trackt.data.Models
import com.example.trackt.data.TracktViewModel
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.PurpleGrey40
import com.example.trackt.ui.theme.TracktBlue1
import com.example.trackt.ui.theme.TracktGray1
import com.example.trackt.ui.theme.TracktPurple11
import com.example.trackt.ui.theme.TracktPurple3
import com.example.trackt.ui.theme.TracktWhite2

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TravelsScreen(navigateToDestination: (Int) -> Unit,
                  addDestination: () -> Unit,
                  viewModel: TracktViewModel = viewModel(factory = AppViewModelProvider.createViewModelInstance() )
) {
    val travelsUIState by viewModel.travelsState.collectAsState()
    //val travelsUIState by viewModel.travelsListUIState.collectAsState()
    val profileName by viewModel.name.collectAsState()
    //val name = viewModel.yourName

    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = false,
                requiresLogo = false
            )
        },
        containerColor = TracktWhite2
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            ProfileMessage("Yooo $profileName")
            Log.v("profile name", profileName)
            Spacer(modifier = Modifier.height(30.dp))
            DestinationBody(destinationList = travelsUIState.travelsList,
                            onDestinationClick = navigateToDestination,
                            onClick = addDestination)
        }
    }
}

@Composable
fun ProfileMessage(name: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
    ) {
        Text(
            text = name,
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
fun DestinationBody(destinationList: List<Models.DestinationResponse>,
                    onDestinationClick: (Int) -> Unit,
                    onClick: () -> Unit
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
                .fillMaxSize()
                .padding(20.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)) {
                    Text(
                        text = "My Destinations",
                        fontFamily = Caudex,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Left,
                        color = TracktPurple11
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = onClick,
                        modifier = Modifier.size(40.dp, 40.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = TracktPurple3,
                            contentColor = TracktPurple11)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "add (destination) button",
                            modifier = Modifier.size(16.dp, 16.dp))
                    }
                }

                if (destinationList.isNotEmpty()){
                    Spacer(modifier = Modifier.height(26.dp))
                    DestinationList(destinationList = destinationList,
                        onClick = {onDestinationClick(it.destinationId)})
                } else{
                    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.travel_by_plane),
                            contentDescription = "default image",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DestinationList(destinationList: List<Models.DestinationResponse>,
                    onClick: (Models.DestinationResponse) -> Unit
){
    LazyVerticalGrid(columns = GridCells.Fixed(2)){
        items(items = destinationList, key = {it.destinationId}){destination ->
            DestinationCard(cardContent = destination,
                modifier = Modifier.clickable { onClick(destination) })
        }
    }
}

@Composable
fun DestinationCard(cardContent: Models.DestinationResponse,
                    modifier: Modifier = Modifier){
    Card(modifier = Modifier.size(150.dp, 190.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = TracktGray1) ,
        elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "location image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .size(140.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = cardContent.destination,
                    fontFamily = Caudex,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = TracktBlue1)
            }
        }
    }
}