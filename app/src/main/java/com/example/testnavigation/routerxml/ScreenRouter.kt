package com.example.testnavigation.routerxml

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.example.testnavigation.R
import com.example.testnavigation.routerxml.Destination.UserDetailsScreen.Companion.FIST_NAME_KEY
import com.example.testnavigation.routerxml.Destination.UserDetailsScreen.Companion.LAST_NAME_KEY
import com.example.testnavigation.xml.ui.dashboard.DashboardFragment
import com.example.testnavigation.xml.ui.details.DetailsFragment
import com.example.testnavigation.xml.ui.home.HomeFragment
import com.example.testnavigation.xml.ui.notifications.NotificationsFragment


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
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}


fun NavController.setupGraph(context: Context) = createGraph(
    startDestination = Destination.HomeScreen.fullRoute
) {
    fragment<HomeFragment>(Destination.HomeScreen.fullRoute) {
        label = context.getStringResource(R.string.title_home)
    }
    fragment<DashboardFragment>(Destination.DashboardScreen.fullRoute) {
        label = context.getStringResource(R.string.title_dashboard)
    }
    fragment<NotificationsFragment>(Destination.NotificationsScreen.fullRoute) {
        label = context.getStringResource(R.string.title_notifications)
    }
    fragment<DetailsFragment>(Destination.UserDetailsScreen().fullRoute) {
        label = context.getStringResource(R.string.title_details)

        argument(FIST_NAME_KEY) {
            type = NavType.StringType
            defaultValue = "fisrt"
        }
        argument(LAST_NAME_KEY) {
            type = NavType.StringType
            defaultValue = "fisrt"
        }

    }
}


fun Context.getStringResource(resourceId: Int): String =
    resources.getString(resourceId)

fun NavController.navigateTo(
    destination: String,
    clearBackStack: Boolean = false,
) {
    navigate(destination) {
        if (clearBackStack) {
            popUpTo(0)
        }

    }
}