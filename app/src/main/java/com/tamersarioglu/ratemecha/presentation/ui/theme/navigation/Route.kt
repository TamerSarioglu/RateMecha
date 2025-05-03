package com.tamersarioglu.ratemecha.presentation.ui.theme.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object MechanicDetail : Route("mechanic")
    object AddReview : Route("add-review")
    object ReviewDetail : Route("review")
    object Profile : Route("profile")
}