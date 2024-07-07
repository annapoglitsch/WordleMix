package com.example.wordlemix.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.wordlemix.reusableItems.AppBars
import com.example.wordlemix.viewModel.SharedViewModel
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.SharedViewModelFactory
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.game.ColorChanger
import com.example.wordlemix.game.GameState
import com.example.wordlemix.ui.theme.WordleMixTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavController, route: String, sharedViewModel: SharedViewModel) {
    val topAppBar = AppBars()

    val gameLogic = GameLogic()
    val word = gameLogic.chooseRandomWord()
    val isDark = sharedViewModel.isDarkBool.collectAsState()
    WordleMixTheme(darkTheme = isDark.value) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                topAppBar.TopAppBar(
                    titleText = "Today's WordleMix", icon = true, navController = navController
                )
            }, content = {
                GameScreenStructure(gameLogic, word, navController, sharedViewModel)
            })
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameScreenStructure(gameLogic: GameLogic, word: String, navController: NavController, sharedViewModel: SharedViewModel) {
    var numberOfTries by remember { mutableIntStateOf(0) }
    val initialFontColor = listOf(Color.Black, Color.Black, Color.Black, Color.Black, Color.Black)
    val initialBackgroundColor =
        listOf(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray)
    var isFinished by remember { mutableStateOf(false) }
    var gameState by remember { mutableStateOf(GameState.IN_PROGRESS) }

    val fontColorsList =
        remember { List(6) { mutableStateListOf(*initialFontColor.toTypedArray()) } }
    val backgroundColorsList =
        remember { List(6) { mutableStateListOf(*initialBackgroundColor.toTypedArray()) } }
    val textFieldLists = remember { List(6) { mutableStateListOf("", "", "", "", "") } }
    var enabledColumnIndex by remember { mutableIntStateOf(0) }
    val colorChanger = ColorChanger()

    val incrementColumnIndex = {
        if (enabledColumnIndex < 4) {
            enabledColumnIndex++

        }
    }


    val incrementNumberOfTries = {
        if (numberOfTries <= 4) {
            numberOfTries++

        }
    }

    var db = PlayerDatabase.getDatabase(LocalContext.current)

    var keyHandler = KeyHandler(
        word,
        gameLogic,
        fontColorsList,
        backgroundColorsList,
        textFieldLists,
        isFinished = { finished -> isFinished = finished },
        colorChanger,
        numberOfTries,
        enabledColumnIndex,
        setGameState = { gameStateParam -> gameState = gameStateParam },
        db = PlayerDatabase.getDatabase(LocalContext.current),
        repository = PlayerRepository(playerDAO = db.playerDao()),
        coroutineScope = rememberCoroutineScope(),
        context = LocalContext.current
    )

    when (gameState) {
        GameState.IN_PROGRESS ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp)
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                colorChanger.updateColors(numberOfTries, backgroundColorsList, fontColorsList)
                //colorChanger.updateColors(numberOfTries, backgroundColorsList)
                Column(modifier = Modifier.padding(top = 60.dp)) {
                    for (i in 0..4) {
                        textfieldTempl(
                            //true,
                            isEnabled = (i == enabledColumnIndex),
                            fontColorsList[i],
                            backgroundColorsList[i],
                            textFieldLists[i],
                            keyHandler,
                            i,
                            incrementColumnIndex,
                            incrementNumberOfTries
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(10.dp), color = Color.Black, thickness = 2.dp
                    )
                    button("Guess", onClick = {
                        keyHandler.handleGuess(incrementColumnIndex, incrementNumberOfTries)
                    })
                }
            }
        GameState.LOST -> losingPanel(25, navController)
        GameState.WON -> winningPanel(25, navController)
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
    focusIndex: Int,
    incrementFocusIndex: () -> Unit,
    incrementNumberOfTries: () -> Unit
) {
    val focusRequesters = List(5) { remember { FocusRequester() } }


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
                            focusRequesters[index + 1].requestFocus()
                        }

                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 10.dp, start = 8.dp, end = 8.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        when (keyEvent.key) {
                            Key.Backspace -> {
                                if (textFieldList[index].isEmpty() && index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                    textFieldList[index - 1] = ""
                                }
                                true
                            }


                            Key.Enter -> {
                                keyHandler.handleGuess(incrementFocusIndex, incrementNumberOfTries)
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
                        keyHandler.handleGuess(incrementFocusIndex, incrementNumberOfTries)
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
            .background(color = MaterialTheme.colorScheme.background)
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

@Composable
fun losingPanel(scoreDecrease: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        //.height(500.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You lost!")
        Text(
            style = TextStyle(fontSize = 25.sp),
            text = "Your score will be decrease by ${scoreDecrease}."
        )
        Spacer(modifier = Modifier.size(30.dp))
        button(buttonText = "Go Back", onClick = { navController.popBackStack() })
    }

}