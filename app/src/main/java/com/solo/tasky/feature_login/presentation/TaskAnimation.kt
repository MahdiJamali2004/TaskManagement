package com.solo.tasky.feature_login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.solo.tasky.R

@Composable
fun TaskAnimation(
    modifier: Modifier = Modifier,
    isClickableAnimation: Boolean = false
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.task_animation))

    if (!isClickableAnimation)
        Box(
            modifier = modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
        }
    else {
        var isPlaying by remember { mutableStateOf(false) }
        val progress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = isPlaying,
        )

        Box(
            modifier = modifier
                .size(160.dp)
                .clip(CircleShape)
                .clickable {
                    isPlaying = true
                },
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                modifier =Modifier
                    .fillMaxWidth()
                    ,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                composition = composition,
                progress = { progress }
            )
        }


        LaunchedEffect(progress) {
            if (progress == 1f) {
                isPlaying = false
            }
        }
    }
}