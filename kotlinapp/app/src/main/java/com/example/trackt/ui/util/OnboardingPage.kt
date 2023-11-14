package com.example.trackt.ui.util

import androidx.annotation.DrawableRes
import com.example.trackt.R

//Resources for the Welcome screen
sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val description: String
) {
    data object First : OnboardingPage(
        image = R.drawable.welcomepic1,
        description = "Your travel \n" + "destinations in one \n" + "place"
    )
    data object Second : OnboardingPage(
        image = R.drawable.welcomepic2,
        description = "Set and track \n" + "your travel \n" + "goals"
    )
    data object Third : OnboardingPage(
        image = R.drawable.welcomepic3,
        description = "Make travel \n" + "itineraries"
    )
}