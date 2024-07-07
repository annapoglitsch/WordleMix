package com.example.wordlemix.game

import android.content.Context
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameLogic {

    var words = listOf(
        "APPLE", "BRAVE", "CHAIR", "DOUBT", "EAGER", "FANCY", "GIANT", "HEART", "INDEX", "JOKER",
        "KNEEL", "LAUGH", "MIGHT", "NIGHT", "OASIS", "PAINT", "QUICK", "RIDER", "SWORD", "THUMB",
        "UNCLE", "VIVID", "WHISK", "YOUNG", "ZEBRA", "ANGEL", "BIRTH", "CRISP", "DREAM", "EARTH",
        "FIELD", "GRAPE", "HONEY", "INNER", "JAZZY", "KNIFE", "LEMON", "MANGO", "NEVER", "OCEAN",
        "PLANT", "QUIET", "RAISE", "STONE", "TRAIN", "USUAL", "VOICE", "WATER", "YEARN", "ZIPPY",
        "ABOVE", "BELOW", "CRANE", "DANCE", "EVERY", "FLUFF", "GREAT", "HUMAN", "INTRO", "JUICE",
        "KNOCK", "LARGE", "MONEY", "NOVEL", "ORGAN", "POWER", "QUILT", "ROUND", "STORM", "THREE",
        "UNITY", "VAGUE", "WHALE", "YACHT", "ZONAL", "ACORN", "BLAZE", "CLICK", "DODGE", "EXACT",
        "FRUIT", "GRILL", "HOTEL", "ICING", "JUMBO", "KAYAK", "LEVEL", "MODEL", "NOBLE", "OZONE"
    )

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
     if (isCorrectWord(word, guess)){
         return GameState.WON
     }
     if (numberOfTries >= 4){
         return GameState.LOST
     }

     return GameState.IN_PROGRESS
 }


 fun receiveScore(gameState: GameState, coroutineScope: CoroutineScope, context: Context, sharedViewModel: SharedViewModel, repository:PlayerRepository) {
     var player: Player;
     player = Player(username = "test", record = 1)
     val playerPreferences: PlayerPreferences = PlayerPreferences(context)

     coroutineScope.launch {
         player = sharedViewModel.getByUsername(playerPreferences.getUsername()!!)

     if (gameState == GameState.WON){
             player.record += 25
     }
     if (gameState == GameState.LOST){
         if ((player.record - 25) <= 0){
             player.record = 0
         }else{
             player.record -= 25
         }
     }
         sharedViewModel.updatePlayer(player)
     }
 }
}