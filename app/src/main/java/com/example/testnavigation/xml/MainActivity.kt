package com.example.testnavigation.xml

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.testnavigation.compose.ComposeActivity
import com.example.testnavigation.R
import com.example.testnavigation.databinding.ActivityMainBinding
import com.example.testnavigation.routerxml.Destination
import com.example.testnavigation.routerxml.setupGraph
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetupNavigation()
        binding.fab.setOnClickListener {
            // Handle FAB click
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }

    private fun initSetupNavigation() {
        // Setup navigation in a single function
        binding.navView.setupNavigation(
            findNavController(R.id.nav_host_fragment_activity_main),
            this
        )
    }

    private fun BottomNavigationView.setupNavigation(
        navController: NavController,
        context: Context
    ) {
        // Safely cast the context to MainActivity
        val activity = context as? MainActivity
            ?: // You can log an error or throw an exception depending on your needs
            throw IllegalArgumentException("Context must be of type MainActivity")
        // Set up NavController
        navController.graph = navController.setupGraph(activity)

        // Set up the BottomNavigationView with NavController
        this.setupWithNavControllerCustom(navController)

        // Set up ActionBar title update based on destination changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val actionBar = activity.supportActionBar
            actionBar?.title = destination.label ?: "Default Title"
        }
    }

    private fun BottomNavigationView.setupWithNavControllerCustom(
        navController: NavController
    ) {
        val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
        builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = true
        )
        val options = builder.build()

        setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(Destination.HomeScreen.fullRoute, options)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_dashboard -> {
                    navController.navigate(Destination.DashboardScreen.fullRoute, options)

                    return@setOnItemSelectedListener true
                }

                R.id.navigation_notifications -> {
                    navController.navigate(Destination.NotificationsScreen.fullRoute, options)

                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
