package com.samadhanl.smartcropadivsorysystem.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samadhanl.smartcropadivsorysystem.Components.DashboardScreen
import com.samadhanl.smartcropadivsorysystem.Components.LoginFlow
import com.samadhanl.smartcropadivsorysystem.Components.WeatherPage


@Composable

//Here is the APP FLOW

fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_flow") {
        // Route for the entire login flow (splash + login screen)
        composable("login_flow") {
            LoginFlow(navController = navController)
        }
        // Route for the main dashboard screen
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }
        composable("weatherUpdates"){
            WeatherPage()
        }
    }
}
