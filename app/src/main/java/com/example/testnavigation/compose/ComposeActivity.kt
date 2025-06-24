package com.example.testnavigation.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.rememberNavController
import com.example.testnavigation.compose.theme.NavigationXmlDSLTheme

class ComposeActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationXmlDSLTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Navigation Example") })
                    },

                    content = { paddingValues ->
                        // No need to pass paddingValues here anymore
                        SetupNavGraph(rememberNavController())
                    }
                )
            }
        }
    }
}
