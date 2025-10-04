package com.capstone2.navigation

interface Navigator {
    fun navigate(route: NavigationCommand)
    fun navigateBack()
    fun popUpTo(route: String, inclusive: Boolean)
}