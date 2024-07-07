package com.example.wordlemix.game

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

class ColorChanger {
    fun updateColors(turn: Int, backgroundColorsList: List<SnapshotStateList<Color>>, fontColorList: List<SnapshotStateList<Color>>) {
        for (i in 0..4) {
            if (i == turn) {
                updateSingleColumn(i, backgroundColorsList, fontColorList, Color.LightGray, Color.Black)
            }
            else {
                updateSingleColumn(i, backgroundColorsList, fontColorList, Color.DarkGray, Color.Black)
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