package com.example.testnavigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.testnavigation.databinding.ActivityMainBinding
import com.example.testnavigation.router.Destination
import com.example.testnavigation.router.setupGraph
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navController.graph = navController.setupGraph(this)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val actionBar = checkNotNull(supportActionBar) {
                "Activity ${this@MainActivity} does not have an ActionBar set via setSupportActionBar()"
            }
            actionBar.title = destination.label

        }
        navView.setupWithNavControllerCustom(navController)

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
