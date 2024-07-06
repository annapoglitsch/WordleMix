package com.example.wordlemix.game

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue

class KeyHandler(
    val word: String,
    val gameLogic: GameLogic,
    var fontColorsList: List<SnapshotStateList<Color>>,
    var backgroundColorsList: List<SnapshotStateList<Color>>,
    var textFieldLists: List<SnapshotStateList<String>>,
    var isFinished: (Boolean) -> Unit,
    val colorChanger: ColorChanger,
    var numberOfTries: Int,
    var enabledColumnIndex: Int,
    var setGameState: (GameState) -> Unit
) {

    fun handleGuess(incrementColumnIndex: () -> Unit, incrementNumberOfTries: () -> Unit) {
        val currentTextFieldList = textFieldLists[numberOfTries]
        val guess: String = currentTextFieldList.joinToString("")
        //println(word)
        //println(numberOfTries)
        //println(gameLogic.isCorrectWord(word, guess))

        //println(gameLogic.checkCorrectLetterPositions(word, guess))
        //println(gameLogic.checkIfLetterInWord(word, guess))

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
        val finished = gameLogic.isCorrectWord(word, guess)
        isFinished(finished)


        //numberOfTries++
        incrementNumberOfTries()
        setGameState(gameLogic.getGameState(numberOfTries, guess, word))
        /*if (enabledColumnIndex < 4) {
            enabledColumnIndex++
        }*/
        incrementColumnIndex()
        println(word)

        /*if (numberOfTries < focusRequesters.size) {
            focusRequesters[numberOfTries][0].requestFocus()
        }*/

        //colorChanger.updateColors(numberOfTries, backgroundColorsList)
    }
}