package com.example.testtask.ui

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.testtask.R
import com.example.testtask.vm.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Composable
fun GameScreen(navController: NavHostController, mViewModel: MainViewModel) {
    val isHealButtonEnabled = mViewModel.isRegenAvailable()
    var isEnabled by remember { mutableStateOf(true) }
    val monsterImageResourceId by remember { mutableIntStateOf(getRandomImageOfMonster()) }
    val playerHealth by remember { mViewModel.getPlayerHealth() }
    val playerAttack = mViewModel.getPlayerAttack()
    val playerDefense = mViewModel.getPlayerDefense()
    val playerDamage = mViewModel.getPlayerDamageRange()
    val monsterHealth by remember { mViewModel.getMonsterHealth() }
    val monsterAttack = mViewModel.getMonsterAttack()
    val monsterDefense = mViewModel.getMonsterDefense()
    val monsterDamage = mViewModel.getMonsterDamageRange()

    Row(
        modifier = Modifier
            .background(color = colorResource(id = R.color.beige))
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CreatePlayerColumn(
            playerHealth.toString(),
            playerAttack.toString(),
            playerDefense.toString(),
            playerDamage.toString(),
            isHealButtonEnabled,
            isEnabled,
            mViewModel,
            LocalContext.current,
            navController
        ) { isEnabled = it }
        CreateMonsterColumn(
            monsterHealth.toString(),
            monsterAttack.toString(),
            monsterDefense.toString(),
            monsterDamage.toString(),
            isEnabled,
            mViewModel,
            LocalContext.current,
            monsterImageResourceId,
            navController
        ) { isEnabled = it }
    }
}

private fun getRandomImageOfMonster(): Int =
    when (Random.nextInt(11)) {
        0 -> R.drawable.monster0
        1 -> R.drawable.monster1
        2 -> R.drawable.monster2
        3 -> R.drawable.monster3
        4 -> R.drawable.monster4
        5 -> R.drawable.monster5
        6 -> R.drawable.monster6
        7 -> R.drawable.monster7
        8 -> R.drawable.monster8
        9 -> R.drawable.monster9
        10 -> R.drawable.monster10
        else -> R.drawable.monster0
    }

@Composable
fun CreatePlayerColumn(
    health: String,
    attack: String,
    defense: String,
    damage: String,
    isHealButtonEnabled: Boolean,
    isEnabled: Boolean,
    mViewModel: MainViewModel,
    context: Context,
    navController: NavHostController,
    setEnabled: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
    ) {
        CreateColumnOfPlayerCharacteristic(health, attack, defense, damage)
        CreateImageOfHero(R.drawable.player)
        CreatePlayerButton(R.drawable.heal, R.color.purple_500, isHealButtonEnabled, isEnabled) {
            val newHP = mViewModel.regenPlayerHP()
            showActionsResult(context, newHP, EnumActions.PLAYER_HEALING)
            monsterTurn(mViewModel, context, navController, setEnabled)
        }
    }
}

@Composable
fun CreateColumnOfPlayerCharacteristic(
    health: String,
    attack: String,
    defense: String,
    damage: String
) {
    Column(modifier = Modifier.border(2.dp, Color.Black)) {
        CreatePlayerCharacteristic(R.drawable.playerhealth, health)
        CreatePlayerCharacteristic(R.drawable.playerattack, attack)
        CreatePlayerCharacteristic(R.drawable.playerdefense, defense)
        CreatePlayerCharacteristic(R.drawable.playerdamage, damage)
    }
}

@Composable
fun CreateImageOfHero(imgId: Int) {
    Column(modifier = Modifier.border(2.dp, Color.Black)) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "image",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        )
    }
}

@Composable
fun CreatePlayerCharacteristic(imgId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth(0.2f)
        )
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            val size = if (text.length > 12) 8
            else 15
            Text(
                text = text,
                fontSize = size.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CreateMonsterColumn(
    health: String,
    attack: String,
    defense: String,
    damage: String,
    isEnabled: Boolean,
    mViewModel: MainViewModel,
    context: Context,
    monsterImageResourceId: Int,
    navController: NavHostController,
    setEnabled: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        CreateColumnOfMonsterCharacteristic(health, attack, defense, damage)
        CreateImageOfHero(monsterImageResourceId)
        CreatePlayerButton(R.drawable.perfomattack, R.color.purple_500, true, isEnabled) {
            val damageDealt = mViewModel.attackTheMonster()
            showActionsResult(context, damageDealt, EnumActions.PLAYER_ATTACKING)
            if (mViewModel.isMonsterDied()) gameIsEnd(
                context,
                EnumActions.MONSTER_DIED,
                navController
            )
            else monsterTurn(mViewModel, context, navController, setEnabled)
        }
    }
}

@Composable
fun CreateColumnOfMonsterCharacteristic(
    health: String,
    attack: String,
    defense: String,
    damage: String
) {
    Column(modifier = Modifier.border(2.dp, Color.Black)) {
        CreateMonsterCharacteristic(R.drawable.monsterhealth, health)
        CreateMonsterCharacteristic(R.drawable.monsterattack, attack)
        CreateMonsterCharacteristic(R.drawable.monsterdefense, defense)
        CreateMonsterCharacteristic(R.drawable.monsterdamage, damage)
    }
}

@Composable
fun CreateMonsterCharacteristic(imgId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(0.8f),
            contentAlignment = Alignment.CenterEnd
        ) {
            val size = if (text.length > 12) 8
            else 15
            Text(
                text = text,
                fontSize = size.sp,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun CreatePlayerButton(
    imgId: Int,
    color: Int,
    isHealButtonEnabled: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxSize()
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = color)
            ),
            onClick = {
                onClick()
            },
            shape = CircleShape,
            border = BorderStroke(2.dp, Color.Black),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            enabled = isHealButtonEnabled && isEnabled
        ) {
            Image(
                painter = painterResource(id = imgId),
                contentDescription = "image",
            )
        }
    }
}

private fun monsterTurn(
    mViewModel: MainViewModel,
    context: Context,
    navController: NavHostController,
    setEnabled: (Boolean) -> Unit
) {
    mViewModel.viewModelScope.launch {
        setEnabled(false)
        delay(3000)
        withContext(Dispatchers.Main) {
            val damageTaken = mViewModel.protectFromMonster()
            showActionsResult(context, damageTaken, EnumActions.MONSTER_ATTACKING)
            delay(3000)
            setEnabled(true)
            if (mViewModel.isPlayerDied()) gameIsEnd(
                context,
                EnumActions.PLAYER_IS_DIED,
                navController
            )
        }
    }
}

private fun showActionsResult(context: Context, value: Int, action: EnumActions) {
    val text: String = when (action) {
        EnumActions.PLAYER_ATTACKING -> if (value > 0) context.getString(
            R.string.player_attacking_success,
            value
        )
        else context.getString(R.string.player_attacking_failure)

        EnumActions.MONSTER_ATTACKING -> if (value > 0) context.getString(
            R.string.monster_attacking_success,
            value
        )
        else context.getString(R.string.monster_attacking_failure)

        EnumActions.PLAYER_HEALING -> context.getString(R.string.player_healing, value)
        else -> {
            ""
        }
    }
    val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
    toast.show()
}

private fun gameIsEnd(context: Context, action: EnumActions, navController: NavHostController) {
    val text: String = when (action) {
        EnumActions.PLAYER_IS_DIED -> context.getString(R.string.playerIsDied)
        EnumActions.MONSTER_DIED -> context.getString(R.string.playerIsDied)
        else -> {
            ""
        }
    }
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Game is end!")
    builder.setMessage(text)
    builder.setPositiveButton("On the menu") { dialog, _ ->
        navController.navigate("Menu") { popUpTo("Menu") }
        dialog.dismiss()
    }
    builder.setCancelable(false)
    val dialog = builder.create()
    dialog.show()
}
