package com.capstone2.navigation

sealed class NavigationRoutes(val route: String) {
    data object SignIn : NavigationRoutes("sign_in")
    data object SignUp : NavigationRoutes("sign_up")
    data object SignUpComplete : NavigationRoutes("sign_up_complete")
    data object Home : NavigationRoutes("home")
    data object Presentation : NavigationRoutes("presentation")
    data object PresentationResult : NavigationRoutes("presentation_result")
}