package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.Black
import com.maccha_koubou.transition_guidepost.ui.theme.DarkPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.tabActiveIndicatorColors
import com.maccha_koubou.transition_guidepost.ui.theme.tabInactiveIndicatorColors

@Composable
fun BottomTab(selected: Boolean, onClick: () -> Unit, title: String) {

    Column(
        Modifier
            .height(48.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            colors = when (selected)
            {
                true -> tabActiveIndicatorColors
                false -> tabInactiveIndicatorColors
            },
            shape = RoundedCornerShape(0.dp, 0.dp, 2.dp, 2.dp)
        ) { }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = Typography.titleMedium,
                color = when (selected) {
                    true -> Black
                    false -> Gray
                }
            )
        }
    }
}

@Composable
fun BottomTabRow(
    typeState: Int,
    tabTitles: List<String>,
    onTabClick: (Int) -> Unit
) {
    Column {
        TabRow(
            selectedTabIndex = typeState,
            indicator = {},
            divider = {},
            containerColor = White
        ) {
            tabTitles.forEachIndexed { index, title ->
                BottomTab(
                    selected = typeState == index,
                    onClick = { onTabClick(typeState) },
                    title = title
                )
            }
        }
    }
}