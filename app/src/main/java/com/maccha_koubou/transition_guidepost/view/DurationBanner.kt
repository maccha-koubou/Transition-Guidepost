package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.service.duration
import com.maccha_koubou.transition_guidepost.storage.user
import com.maccha_koubou.transition_guidepost.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun DurationBanner() {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            Modifier
                .padding(0.dp, 8.dp)
                .clickable { /* GAHT-starting date inputer */ }
                .weight(1f)
        ) {
            Text(
                text = LocalDate.now().format(
                    DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                ),
                style = Typography.bodySmall
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    //Show a welcome text if the duration is null
                    text = user.duration()?.toString() ?: "你好！",
                    style = Typography.headlineLarge
                    )
                Text(
                    //Show a welcome text if the duration is null
                    text = when (user.duration()) {
                        null -> "点击以记录您的激素肯定治疗天数"
                        else -> "天激素肯定治疗时长"
                    },
                    style = Typography.bodyMedium
                )
            }
        }

        //An button for more functions, including Login, Sharing data and Settings
        IconButton(
            onClick = { /* More Options Menu */ },
            modifier = Modifier.size(48.dp),
            colors = largeButtonColors
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "More options"
            )
        }
    }
}