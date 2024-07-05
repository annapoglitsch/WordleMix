package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wordlemix.appbars.AppBars
import com.example.wordlemix.game.GameLogic

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavController, route: String) {
    val topAppBar = AppBars()
    val gameLogic = GameLogic()
    val word = gameLogic.chooseRandomWord()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                topAppBar.TopAppBar(titleText = "Today's WordleMix", icon = true, navController = navController)
            }

        ) {
            GameScreenStructure(gameLogic, word)
        }
    }
}

@Composable
fun GameScreenStructure(gameLogic: GameLogic, word: String) {
    var numberOfTries by remember { mutableIntStateOf(0) }
    val initialFontColor = listOf(Color.Black, Color.Black, Color.Black, Color.Black, Color.Black)
    val initialBackgroundColor = listOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray)

    val fontColorsList = remember { List(6) {mutableStateListOf(*initialFontColor.toTypedArray())}}
    val backgroundColorsList = remember { List(6) {mutableStateListOf(*initialBackgroundColor.toTypedArray())}}
    val textFieldLists = remember { List(6) { mutableStateListOf("", "", "", "", "") } }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
            .background(color = Color(0xFFAAD6F3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Column(modifier = Modifier.padding(top = 60.dp)) {
            for (i in 0..5) {
                textfieldTempl(true, fontColorsList[i], backgroundColorsList[i], textFieldLists[i])
            }

            Divider(modifier = Modifier.padding(10.dp),
                color = Color.Black,
                thickness = 2.dp)
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                //println("First Word: ${textFieldList[0]}${textFieldList[1]}${textFieldList[2]}${textFieldList[3]}${textFieldList[4]} ")
                    val currentTextFieldList = textFieldLists[numberOfTries]
                    val guess: String = currentTextFieldList.joinToString("")
                    println(word)
                    println(gameLogic.isCorrectWord(word, guess))
                    println(gameLogic.checkCorrectLetterPositions(word, guess))
                    println(gameLogic.checkIfLetterInWord(word, guess))
                    val correctLetterPositions = gameLogic.checkCorrectLetterPositions(word, guess)
                    val letterInWordPositions = gameLogic.checkIfLetterInWord(word, guess)

                    correctLetterPositions.forEach { index ->
                        fontColorsList[numberOfTries][index] = Color.Black
                        backgroundColorsList[numberOfTries][index] = Color.Green
                    }
                    letterInWordPositions.forEach { index ->
                        if (!correctLetterPositions.contains(index)) {
                            fontColorsList[numberOfTries][index] = Color.Black
                            backgroundColorsList[numberOfTries][index] = Color.Yellow
                        }
                    }
                    numberOfTries++
            }) {
                Text("Guess")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textfieldTempl(isEnabled: Boolean, fontColors: SnapshotStateList<Color>, backgroundColors: SnapshotStateList<Color>, textFieldList: SnapshotStateList<String>) {
    Row {
        textFieldList.forEachIndexed { index, text ->
           TextField(
                textStyle = TextStyle(color = fontColors[index], fontSize = 30.sp, textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(containerColor = backgroundColors[index]),
                value = text,
                enabled = isEnabled,
                onValueChange = { newText -> if (newText.length <= 1){
                    textFieldList[index] = newText
                }
                                                              },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp),

            )
        }
   }
}

@Preview
@Composable
fun winningPanel(scoreIncrease: Int = 2){
    Column(modifier = Modifier
        .background(color = Color.Gray.copy(alpha = 0.5f))
        .width(200.dp)
        .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "You won!")
        Text(text = "Your score will be added by ${scoreIncrease}.")
    }
}