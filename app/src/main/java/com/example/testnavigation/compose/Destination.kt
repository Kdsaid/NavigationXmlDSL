package com.example.testnavigation.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.testnavigation.R
import com.example.testnavigation.compose.screens.detailsScreen.DetailsScreen
import com.example.testnavigation.compose.screens.homeScreen.HomeScreen

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object HomeScreen : NoArgumentsDestination(HOME_ROUTE)

    data object DashboardScreen : NoArgumentsDestination(DASHBOARD_ROUTE)

    data object NotificationsScreen : NoArgumentsDestination(NOTIFICATIONS_ROUTE)

    class UserDetailsScreen : Destination(DETAIL_ROUTE, FIST_NAME_KEY, LAST_NAME_KEY) {
        operator fun invoke(fistName: String, lastName: String): String = route.appendParams(
            FIST_NAME_KEY to fistName,
            LAST_NAME_KEY to lastName
        )

        companion object {
            const val FIST_NAME_KEY = "firstName"
            const val LAST_NAME_KEY = "lastName"
        }
    }

    companion object {
        private const val HOME_ROUTE = "home"
        private const val DASHBOARD_ROUTE = "dashboard"
        private const val NOTIFICATIONS_ROUTE = "notifications"
        private const val DETAIL_ROUTE = "details_details"
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)
    params.forEach {
        it.second?.toString()?.let { arg -> builder.append("/$arg") }
    }
    return builder.toString()
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.HomeScreen.fullRoute) {
        composable(Destination.HomeScreen.fullRoute) {
            HomeScreen(navController)
        }

        composable(
            Destination.UserDetailsScreen().fullRoute,
            arguments = listOf(
                navArgument(Destination.UserDetailsScreen.FIST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "first"
                },
                navArgument(Destination.UserDetailsScreen.LAST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "last"
                }
            )
        ) { backStackEntry ->
            DetailsScreen(backStackEntry)
        }
    }
}


fun NavHostController.navigateTo(
    destination: String,
    clearBackStack: Boolean = false
) {
    navigate(destination) {
        if (clearBackStack) {
            popUpTo(0)
        }
    }
}
