package com.example.trackt.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackt.data.AppViewModelProvider
import com.example.trackt.data.TracktViewModel
import com.example.trackt.ui.navigation.NavigationDestination
import com.example.trackt.ui.theme.Caudex
import com.example.trackt.ui.theme.Shapes
import com.example.trackt.ui.theme.TracktBlue1
import com.example.trackt.ui.theme.TracktPink1
import com.example.trackt.ui.theme.TracktPurple1
import com.example.trackt.ui.util.OnboardingPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

object WelcomeScreen : NavigationDestination {
    override val route = "welcome"
}

//one page screen for the welcome screens
@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: TracktViewModel = viewModel(factory = AppViewModelProvider.createViewModelInstance())
) {

    val context = LocalContext.current

    val pages = listOf(
        OnboardingPage.First,
        OnboardingPage.Second,
        OnboardingPage.Third
    )
    val pagerState = rememberPagerState()

    Column (modifier = Modifier.fillMaxSize()){
        HorizontalPager(
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.weight(10f)
        ) {
            position -> PagerScreen(onboardingPage = pages[position])
        }
        GetStartedButton(modifier = Modifier.weight(1f),
            pagerState = pagerState) {
            viewModel.saveOnboardingState(completed = true, context = context)
            navController.popBackStack()
            navController.navigate(SignupScreen.route)
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(.5f),
            pagerState = pagerState
        )
    }
}

@Composable
fun PagerScreen(onboardingPage: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            Modifier
                .requiredSize(550.dp, 450.dp)
                .clip(Shapes.small)
        ) {
            Image(
                modifier = Modifier.requiredSize(550.dp, 550.dp),
                painter = painterResource(id = onboardingPage.image),
                contentDescription = "Pager Image",
                contentScale = ContentScale.FillHeight
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 32.dp),
            text = onboardingPage.description,
            fontFamily = Caudex,
            fontSize = 30.sp,
            textAlign = TextAlign.Left,
            color = TracktBlue1
        )
    }
}

//button displays when onboarding slide ends
@OptIn(ExperimentalPagerApi::class)
@Composable
fun GetStartedButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier.padding(40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ){
        AnimatedVisibility(
            visible = pagerState.currentPage == 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TracktPink1,
                    contentColor = TracktPurple1
                )
            ) {
                Text(text = "Get Started",
                    fontFamily = Caudex,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.Third)
    }
}