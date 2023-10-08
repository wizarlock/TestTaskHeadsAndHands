package com.example.testtask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testtask.vm.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()
            NavHost(
                navController = navController,
                startDestination = "Menu"
            ) {
                composable("Menu") { MenuScreen(navController) }
                composable("PreStart") { PreStartScreen(navController, viewModel) }
                composable("Game") { GameScreen(navController, viewModel) }
                composable("Rules") { RulesScreen() }
            }
        }
    }
}
