package com.example.wordlemix.game

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color

class KeyHandler(
    var numberOfTries: Int,
    val word: String,
    val gameLogic: GameLogic,
    var fontColorsList: List<SnapshotStateList<Color>>,
    var backgroundColorsList: List<SnapshotStateList<Color>>,
    var textFieldLists: List<SnapshotStateList<String>>,
    var isFinished: (Boolean) -> Unit,
    val focusRequesters: List<List<FocusRequester>>
) {

    fun handleGuess() {
        val currentTextFieldList = textFieldLists[numberOfTries]
        val guess: String = currentTextFieldList.joinToString("")
        println(word)
        println(numberOfTries)
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
        val finished = gameLogic.isCorrectWord(word, guess)
        isFinished(finished)

        numberOfTries++

        if (numberOfTries < focusRequesters.size) {
            focusRequesters[numberOfTries][0].requestFocus()
        }
    }
}