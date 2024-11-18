package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.*


// Empty card with tab bar
@Composable
fun DataCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        cardColors
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            content()
            DataTypeTabBar()
        }
    }
}

@Preview
@Composable
fun DataTypeTabBar() {
    var state by remember { mutableStateOf(0) }
    val tabTitles = listOf("用药", "生活", "健康")
    Column {
        TabRow(selectedTabIndex = state) {
            tabTitles.forEachIndexed { index, title ->
                BottomTab(
                    selected = state == index,
                    onClick = { state = index },
                    title = title
                )
            }
        }
    }
}

@Preview
@Composable
fun EmptyDataCard() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            // Title of the card
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 4.dp ,0.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "我的激素水平",
                    style = Typography.titleLarge
                )
            }

            // Add data button & Description
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp, 16.dp ,16.dp)
                    .border(1.dp, LightPurple, RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = "添加数据后，\n您就可以在这里\n通过图表追踪激素数据",
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { /* Add data */ },
                    colors = largeMainButtonColors
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "添加数据",
                        style = Typography.titleSmall,
                        color = White
                    )
                }
            }
        }
    }
}