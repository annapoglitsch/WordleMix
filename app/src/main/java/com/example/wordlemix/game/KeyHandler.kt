package com.example.wordlemix.game

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope

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
    var setGameState: (GameState) -> Unit,
    val db: PlayerDatabase,
    val repository: PlayerRepository,
    val coroutineScope: CoroutineScope,
    val sharedViewModel: SharedViewModel,
    val context: Context,
    val setJokerState: () -> Unit
) {

    fun handleGuess(incrementColumnIndex: () -> Unit, incrementNumberOfTries: () -> Unit) {
        val currentTextFieldList = textFieldLists[numberOfTries]
        val guess: String = currentTextFieldList.joinToString("")

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


        incrementNumberOfTries()
        setGameState(gameLogic.getGameState(numberOfTries, guess, word))
        gameLogic.receiveScore(gameLogic.getGameState(numberOfTries, guess, word), coroutineScope, context, sharedViewModel ,repository)
        incrementColumnIndex()
        println(word)

    }

    fun handleJoker() {
        val currentTextFieldList = textFieldLists[numberOfTries]
        for (i in 0 until minOf(3, word.length)) {
            currentTextFieldList[i] = word[i].toString()

        }

        setJokerState()
    }
}