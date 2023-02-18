package com.volokhinaleksey.materialdesign.ui.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import kotlinx.coroutines.delay

/**
 * Application Start Screen
 * @param navController - object for working with navigation
 */

@Composable
fun SplashScreen(navController: NavController) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val animationAlpha = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutLinearInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(ScreenState.PictureOfDayScreen.route)
    }

    Splash(alpha = animationAlpha.value)
}

@Composable
fun Splash(alpha: Float) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme())
                    MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.secondaryContainer
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Application logotype",
            modifier = Modifier
                .size(200.dp)
                .alpha(alpha = alpha),
            tint = Color.White
        )

    }

}

@Composable
@Preview(showBackground = true)
fun SplashPreviewDayMode() {
    Splash(alpha = 1f)
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun SplashPreviewDarkMode() {
    Splash(alpha = 1f)
}