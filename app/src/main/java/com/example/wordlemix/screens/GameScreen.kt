package com.example.wordlemix.screens

import android.annotation.SuppressLint
import android.view.KeyEvent.KEYCODE_DEL
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import android.view.KeyEvent as AndroidKeyEvent
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wordlemix.appbars.AppBars
import com.example.wordlemix.game.GameLogic
import com.example.wordlemix.game.KeyHandler
import com.example.wordlemix.reusableItems.button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.unit.dp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavController, route: String) {
    val topAppBar = AppBars()
    val gameLogic = GameLogic()
    val word = gameLogic.chooseRandomWord()
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            topAppBar.TopAppBar(
                titleText = "Today's WordleMix", icon = true, navController = navController
            )
        }, content = {
            GameScreenStructure(gameLogic, word, navController)
        })
    }
}

@Composable
fun GameScreenStructure(gameLogic: GameLogic, word: String, navController: NavController) {
    var numberOfTries by remember { mutableIntStateOf(0) }
    val initialFontColor = listOf(Color.Black, Color.Black, Color.Black, Color.Black, Color.Black)
    val initialBackgroundColor =
        listOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray)
    var isFinished by remember { mutableStateOf(false) }

    val fontColorsList =
        remember { List(6) { mutableStateListOf(*initialFontColor.toTypedArray()) } }
    val backgroundColorsList =
        remember { List(6) { mutableStateListOf(*initialBackgroundColor.toTypedArray()) } }
    val textFieldLists = remember { List(6) { mutableStateListOf("", "", "", "", "") } }
    val focusRequesters = remember { List(5) { List(5) { FocusRequester() } } }
    var enabledColumnIndex by remember { mutableIntStateOf(0) }

    var keyHandler = KeyHandler(
        numberOfTries,
        word,
        gameLogic,
        fontColorsList,
        backgroundColorsList,
        textFieldLists,
        isFinished = { finished -> isFinished = finished },
        focusRequesters
    )



    if (!isFinished) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
                .background(color = Color(0xFFAAD6F3)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Column(modifier = Modifier.padding(top = 60.dp)) {
                for (i in 0..4) {
                    textfieldTempl(
                        isEnabled = (i == enabledColumnIndex),
                        fontColorsList[i],
                        backgroundColorsList[i],
                        textFieldLists[i],
                        keyHandler,
                        focusRequesters,
                        i
                    )
                }

                Divider(
                    modifier = Modifier.padding(10.dp), color = Color.Black, thickness = 2.dp
                )
                Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {

                    keyHandler.handleGuess()
                    if (enabledColumnIndex < 4) {
                        enabledColumnIndex++
                    }

                }) {
                    Text("Guess")
                }
            }
        }
    } else {
        winningPanel(5, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textfieldTempl(
    isEnabled: Boolean,
    fontColors: SnapshotStateList<Color>,
    backgroundColors: SnapshotStateList<Color>,
    textFieldList: SnapshotStateList<String>,
    keyHandler: KeyHandler,
    focusRequesters: List<List<FocusRequester>>,
    focusIndex: Int,
) {
    //val focusRequesters = List(5) { remember { FocusRequester() } }


    Row {
        textFieldList.forEachIndexed { index, text ->
            TextField(textStyle = TextStyle(
                color = fontColors[index], fontSize = 30.sp, textAlign = TextAlign.Center
            ),
                colors = TextFieldDefaults.textFieldColors(containerColor = backgroundColors[index]),
                value = text,
                enabled = isEnabled,
                onValueChange = { newText ->
                    if (newText.length <= 1) {
                        textFieldList[index] = newText
                        if (newText.isNotEmpty() && index < 4) {
                            focusRequesters[focusIndex][index + 1].requestFocus()
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp)
                    .focusRequester(focusRequesters[focusIndex][index])
                    .onKeyEvent { keyEvent ->
                        when (keyEvent.key) {
                            Key.Backspace -> {
                                if (textFieldList[index].isEmpty() && index > 0) {
                                    focusRequesters[focusIndex][index - 1].requestFocus()
                                    textFieldList[index - 1] = ""
                                }
                                true
                            }
                            Key.Enter -> {
                                keyHandler.handleGuess()
                                true
                            }
                            else -> false
                        }
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyHandler.handleGuess()
                    }
                )
            )/*.onKeyEvent { keyEvent ->
                println("Key code: ${keyEvent}")
            false})*/
        }
    }

}

@Composable
fun winningPanel(scoreIncrease: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = Color.Gray.copy(alpha = 0.5f))
            .fillMaxSize(),
        //.height(500.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You won!")
        Text(
            style = TextStyle(fontSize = 25.sp),
            text = "Your score will be increased by ${scoreIncrease}."
        )
        Spacer(modifier = Modifier.size(30.dp))
        button(buttonText = "Go Back", onClick = { navController.popBackStack() })
    }
}