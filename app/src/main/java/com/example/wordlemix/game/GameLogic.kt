package com.example.wordlemix.game

import java.io.File
import java.nio.file.Path
import kotlin.random.Random

class GameLogic {

    /*val jsonFile = File("game/words.json")
    val jsonString = jsonFile.readText()
    val gson = Gson()
    val wordObject: GuessWords = gson.fromJson(jsonString, GuessWords::class.java)*/
    var words = listOf("APPLE", "CLOUD", "SMART", "TOKYO")
    val MAX_ATTEMPTS = 5

    fun printWords() {
        println(words)
    }

    fun chooseRandomWord(): String {
        if (words.isEmpty()) {
            throw IllegalArgumentException("The list of words is empty.")
        }
        val randomIndex = Random.nextInt(words.size)
        return words[randomIndex]
    }

    fun isCorrectWord(word: String, guess: String): Boolean {
        return word == guess
    }

    fun checkCorrectLetterPositions(word: String, guess: String): List<Int> {
        val correctPositions = mutableListOf<Int>()
        for (index in word.indices) {
            if (index < guess.length && word[index] == guess[index]) {
                correctPositions.add(index)
            }
        }

        return correctPositions
    }

    fun checkIfLetterInWord(word: String, guess: String): List<Int> {
        val result = mutableListOf<Int>()

        for (index in guess.indices) {
            if (word.contains(guess[index])) {
                result.add(index)
            }
        }

        return result
    }

    fun getGameState(numberOfTries: Int, guess: String, word: String): GameState {
        if (isCorrectWord(word, guess))
            return GameState.WON
        if (numberOfTries >= 4)
            return GameState.LOST

        return GameState.IN_PROGRESS
    }
}