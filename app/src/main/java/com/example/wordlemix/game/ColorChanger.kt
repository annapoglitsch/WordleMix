package com.example.wordlemix.game

import androidx.compose.animation.core.updateTransition
import  androidx.compose.ui.graphics.Color.Companion
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.wordlemix.screens.textfieldTempl

class ColorChanger {
    fun updateColors(turn: Int, backgroundColorsList: List<SnapshotStateList<Color>>, fontColorList: List<SnapshotStateList<Color>>) {
        for (i in 0..4) {
            if (i == turn) {
                updateSingleColumn(i, backgroundColorsList, fontColorList, Color.LightGray, Color.Black)
            }
            else {
                updateSingleColumn(i, backgroundColorsList, fontColorList, Color.DarkGray, Color.White)
            }
        }
    }

    fun updateSingleColumn(columnNumber: Int, backgroundColorsList: List<SnapshotStateList<Color>>, fontColorList: List<SnapshotStateList<Color>>, color: Color, fontColor: Color) {
        for (i in 0..4) {
            if (backgroundColorsList[columnNumber][i] != Color.Yellow && backgroundColorsList[columnNumber][i] != Color.Green)
                backgroundColorsList[columnNumber][i] = color
            fontColorList[columnNumber][i] = fontColor
        }
    }
}