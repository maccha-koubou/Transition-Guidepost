package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.IconButtonColors

@Composable
fun AddDataIconButton(event: () -> Unit) {
    IconButton(
        onClick = { event() },
        modifier = Modifier
            .size(48.dp)
            .padding(12.dp),
        colors = AddButtonColors
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "添加数据"
        )
    }
}

@Composable
fun ChartListSwitcher(isList: Boolean, state: (Boolean) -> Unit) {
    Box{
        when (isList) {

            //Switch to chart
            true ->
                IconButton(
                    onClick = { state(false) },
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonColors
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "切换为图表显示"
                    )
                }

            //Switch to list
            false ->
                IconButton(
                    onClick = { state(true) },
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonColors
                ) {
                    Icon(
                        Icons.Filled.List,
                        contentDescription = "切换为列表显示"
                    )
                }
        }
    }
}