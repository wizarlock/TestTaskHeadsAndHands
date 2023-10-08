package com.example.testtask.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtask.R

@Composable
fun CreateText(text: String, pad: Int, size: Int, color: Color) {
    Box(
        modifier = Modifier.padding(top = pad.dp)
    ) {
        Text(
            text = text,
            fontSize = size.sp,
            color = color
        )
    }
}

@Composable
fun CreateButton(text: String, pad: Int, width: Int, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.gray)),
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .padding(top = pad.dp)
            .widthIn(min = width.dp)
    ) {
        Text(
            text = text,
            fontSize = 25.sp,
            color = Color.White
        )
    }
}