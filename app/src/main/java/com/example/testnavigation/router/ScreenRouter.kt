package com.example.testnavigation.router

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.example.testnavigation.R
import com.example.testnavigation.ui.dashboard.DashboardFragment
import com.example.testnavigation.ui.home.HomeFragment
import com.example.testnavigation.ui.notifications.NotificationsFragment

sealed class Routes(val route: String) {
    object NavigationHome : Routes("navigation_home")
    object NavigationDashboard : Routes("navigation_dashboard")
    object NavigationNotifications : Routes("navigation_notifications")

}

fun NavController.setupGraph(context: Context) = createGraph(
    startDestination = Routes.NavigationHome.route
) {
    fragment<HomeFragment>(Routes.NavigationHome.route) {
        label = context.getStringResource(R.string.title_home)
    }
    fragment<DashboardFragment>(Routes.NavigationDashboard.route) {
        label = context.getStringResource(R.string.title_dashboard)
    }
    fragment<NotificationsFragment>(Routes.NavigationNotifications.route) {
        label = context.getStringResource(R.string.title_notifications)
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