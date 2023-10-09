@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.testtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testtask.R
import com.example.testtask.vm.MainViewModel

@Composable
fun PreStartScreen(navController: NavHostController, mViewModel: MainViewModel) {
    var attack by remember { mutableIntStateOf(0) }
    var defense by remember { mutableIntStateOf(0) }
    var health by remember { mutableIntStateOf(0) }
    var minDamage by remember { mutableIntStateOf(0) }
    var maxDamage by remember { mutableIntStateOf(0) }
    var allFieldsFilled by remember { mutableStateOf(false) }

    fun checkAllFieldsFilled() {
        allFieldsFilled =
            attack != 0 && defense != 0 && health != 0 && minDamage != 0 && maxDamage != 0
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = colorResource(id = R.color.beige))
            .fillMaxSize()
            .padding(45.dp)
    ) {
        CreateText("Set up your hero", 0, 35, Color.White)
        Spacer(modifier = Modifier.size(15.dp))
        CreateTextField("Attack", stringResource(R.string.hintForAttack), attack) {
            attack = it
            checkAllFieldsFilled()
        }
        CreateTextField("Defense", stringResource(R.string.hintForDefense), defense) {
            defense = it
            checkAllFieldsFilled()
        }
        CreateTextField("Health", stringResource(R.string.hintForHealth), health) {
            health = it
            checkAllFieldsFilled()
        }
        CreateTextField(
            "Min Damage", stringResource(R.string.hintForMinDamage), minDamage
        ) {
            minDamage = it
            checkAllFieldsFilled()
        }
        CreateTextField(
            "Max Damage", stringResource(R.string.hintForMaxDamage), maxDamage
        ) {
            maxDamage = it
            checkAllFieldsFilled()
        }

        if (allFieldsFilled)
            if (mViewModel.isValidInputData(
                    attack,
                    defense,
                    health,
                    IntRange(minDamage, maxDamage)
                )
            ) {
                CreateButton("Let's Fight!", 30, 275) {
                    mViewModel.createPlayer(attack, defense, health, IntRange(minDamage, maxDamage))
                    navController.navigate("Game")
                }
            } else CreateText("Enter correct characteristics", 20, 20, Color.Red)
    }
}


@Composable
fun CreateTextField(label: String, hint: String, text: Int, onValueChange: (Int) -> Unit) {
    var isHintVisible by remember { mutableStateOf(text == 0) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        OutlinedTextField(
            value = text.takeIf { it != 0 }?.toString() ?: "",
            onValueChange = {
                val newVal = it.toIntOrNull() ?: 0
                onValueChange(newVal)
                isHintVisible = newVal == 0
            },
            label = {
                Text(
                    text = label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                textColor = Color.Black,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )
        if (isHintVisible) CreateText(hint, 3, 11, Color.Gray)
    }
}
