package com.example.wordlemix.game
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordlemix.screens.StartScreenStructure
import com.google.gson.Gson
import java.io.File

class GameLogic {
    val jsonFile = File("game/words.kt")
    val jsonString = jsonFile.readText()
    val gson = Gson()
    val wordsObject:GuessWords = gson.fromJson(jsonString, GuessWords::class.java)

    @Preview
    @Composable
    fun Game(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            changeColor(userInput = TextfieldTemplate(), toGuessWord = chooseRandomWord())
            changeColor(userInput = TextfieldTemplate(), toGuessWord = chooseRandomWord())
            changeColor(userInput = TextfieldTemplate(), toGuessWord = chooseRandomWord())
            changeColor(userInput = TextfieldTemplate(), toGuessWord = chooseRandomWord())
            changeColor(userInput = TextfieldTemplate(), toGuessWord = chooseRandomWord())
        }
    }

    fun chooseRandomWord(): String{
        var count = 0
        for (words in wordsObject.words){
            count += 1
        }
        val random = (0..count).random()

        val toGuessWord = wordsObject.words[random]
        return toGuessWord
    }
   /* @Composable
    fun win(userInput: String, toGuessWord: String){
        if (userInput == toGuessWord){
            Text(text = "Congratulation! You Won!")
        }
    }*/

    @Composable
    fun changeColor(userInput: Unit, toGuessWord: String) : String{
        val userInputString = userInput.toString()
        val changedText = if (userInputString == toGuessWord){
            buildAnnotatedString {
                withStyle(style = SpanStyle(background = Color.Green)){
                    append(userInputString)
                }
            }
        } else {
            buildAnnotatedString {
                withStyle(style = SpanStyle(background = Color.Gray)){
                    append(userInputString)
                }
            }
        }
        val changedTextString = changedText.toString()
        return changedTextString
    }

    @Composable
    fun TextfieldTemplate(){
        var text by remember{mutableStateOf("")}
        TextField(value = text, onValueChange = {text = it})
    }
}