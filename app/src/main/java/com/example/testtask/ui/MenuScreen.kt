package com.example.testtask.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testtask.R

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = colorResource(id = R.color.beige))
            .fillMaxSize()
    ) {
        CreateText("Let's the battle begin!", 200, 35, Color.White)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 60.dp)
        ) {
            CreateButton("Play", 20, 275) {
                navController.navigate("PreStart")
            }
            CreateButton("Rules", 20, 275) {
                navController.navigate("Rules")
            }
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            CreateImage(R.drawable.knightrev)
            CreateImage(R.drawable.knight)
        }
    }
}

@Composable
fun CreateImage(imageId: Int) {
    Box() {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "image"
        )
    }
}