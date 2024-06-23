package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
            .background(color = Color(0xFFAAD6F3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Column(modifier = Modifier.padding(top = 60.dp)) {
            val textFieldList = textfieldTempl(true)
            val textFieldList2 = textfieldTempl(true)
            val textFieldList3 = textfieldTempl(true)
            val textFieldList4 = textfieldTempl(true)
            val textFieldList5 = textfieldTempl(true)
            val textFieldList6 = textfieldTempl(true)

            val list = mutableListOf<SnapshotStateList<String>>()
            list.add(textFieldList)
            list.add(textFieldList2)
            list.add(textFieldList3)
            list.add(textFieldList4)
            list.add(textFieldList5)
            list.add(textFieldList6)

            Divider(modifier = Modifier.padding(10.dp),
                color = Color.Black,
                thickness = 2.dp)
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                //println("First Word: ${textFieldList[0]}${textFieldList[1]}${textFieldList[2]}${textFieldList[3]}${textFieldList[4]} ")
                    println("Word: ${list[numberOfTries][0]}${list[numberOfTries][1]}${list[numberOfTries][2]}${list[numberOfTries][3]}${list[numberOfTries][4]}")
                    val guess: String = list[numberOfTries][0] + list[numberOfTries][1] + list[numberOfTries][2] + list[numberOfTries][3] + list[numberOfTries][4]
                    println(word)
                    println(gameLogic.isCorrectWord(word, guess))
                    numberOfTries++
            }) {
                Text("Guess")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textfieldTempl(isEnabled: Boolean): SnapshotStateList<String> {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val textFieldList = remember {
        mutableStateListOf("", "", "", "", "")
    }
    Row {
        textFieldList.forEachIndexed { index, text ->
           TextField(
                textStyle = TextStyle(color = Color.Black, fontSize = 30.sp, textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
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

  //ToDo: Problem lösen -> wie greifen wir auf einen einzelnen Textfieldindex zu

        /*TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(80.dp)
                .padding(8.dp)
        )
        var text2 by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = text2,
            onValueChange = { newText -> text2 = newText },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(80.dp)
                .padding(8.dp)
        )*/

    return textFieldList
}