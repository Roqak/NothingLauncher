package com.nothing.launcher.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

private val ALPHABET = ('A'..'Z').toList()

/**
 * Nothing-style vertical alphabet scroller.
 * Shows dots by default; shows the touched letter when active.
 */
@Composable
fun AlphabetScroller(
    activeLetter: Char?,
    onLetterSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 16.dp, horizontal = 4.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ALPHABET.forEach { letter ->
            val isActive = letter == activeLetter
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .padding(vertical = 1.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isActive) letter.toString() else "•",
                    style = if (isActive) MaterialTheme.typography.bodyMedium
                    else MaterialTheme.typography.bodySmall,
                    color = if (isActive) MaterialTheme.colorScheme.background
                    else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}
