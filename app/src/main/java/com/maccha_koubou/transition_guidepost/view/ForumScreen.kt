package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maccha_koubou.transition_guidepost.ui.theme.BackgroundGray
import com.maccha_koubou.transition_guidepost.ui.theme.Typography

@Preview
@Composable
fun ForumScreen() {
    Column(
        modifier = Modifier
            .background(BackgroundGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "论坛敬请期待",
            style = Typography.bodySmall
        )
    }
}