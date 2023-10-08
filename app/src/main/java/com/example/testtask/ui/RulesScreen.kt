package com.example.testtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testtask.R

@Composable
fun RulesScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = colorResource(id = R.color.beige))
            .fillMaxSize()
            .padding(25.dp)
    ) {
        CreateText("Game Rules", 50, 35, Color.White)
        CreateText(stringResource(id = R.string.rules1), 50, 17, Color.White)
        CreateText(stringResource(id = R.string.rules2), 20, 17, Color.White)
    }
}