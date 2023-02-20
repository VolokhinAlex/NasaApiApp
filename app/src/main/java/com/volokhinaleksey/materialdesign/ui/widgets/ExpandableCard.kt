package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    content: @Composable () -> Unit,
    expandContent: @Composable () -> Unit,
    padding: Dp = 10.dp,
    colors: CardColors = CardDefaults.cardColors()
) {

    var expandedState by remember { mutableStateOf(false) }

    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    Card(
        onClick = {
            expandedState = !expandedState
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = colors
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            content()
            AnimatedVisibility(
                visible = expandedState,
                enter = expandTransition,
                exit = collapseTransition
            ) {
                expandContent()
            }
        }
    }
}