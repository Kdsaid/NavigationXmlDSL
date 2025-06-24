package com.example.testnavigation.compose.screens.detailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.example.testnavigation.compose.Destination

@Composable
fun DetailsScreen(backStackEntry: NavBackStackEntry) {
    // Retrieve the arguments from backStackEntry
    val firstName = backStackEntry.arguments?.getString(Destination.UserDetailsScreen.FIST_NAME_KEY) ?: "Unknown"
    val lastName = backStackEntry.arguments?.getString(Destination.UserDetailsScreen.LAST_NAME_KEY) ?: "Unknown"
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "First Name: $firstName")
        Text(text = "Last Name: $lastName")
    }
}
