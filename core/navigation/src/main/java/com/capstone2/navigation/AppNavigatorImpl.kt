package com.capstone2.navigation

import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.capstone2.navigation.extension.printBackStack
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val navController: NavController
) : Navigator {

    override fun navigate(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToRoute -> {
                navController.navigate(
                    route = command.route.route,
                    navOptions = navOptions {
                        popUpTo(command.route.route) { inclusive = false }
                        launchSingleTop = true
                    })
            }

            is NavigationCommand.ToRouteWithId -> {
                navController.navigate(command.id, command.bundle)
            }

            is NavigationCommand.Back -> navController.navigateUp()
            is NavigationCommand.PopUpTo -> {
                navController.popBackStack(command.route.route, command.inclusive)
            }

            is NavigationCommand.ToRouteAndClear -> {
                navController.navigate(
                    route = command.route.route,
                    navOptions = navOptions {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                )
            }
        }

        navController.printBackStack()
    }

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun popUpTo(route: String, inclusive: Boolean) {
        navController.popBackStack(route, inclusive)
    }
}