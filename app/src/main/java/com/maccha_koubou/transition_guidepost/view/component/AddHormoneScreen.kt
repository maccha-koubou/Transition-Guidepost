package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.storage.user
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.titleBarColors
import com.maccha_koubou.transition_guidepost.view.localNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputHormoneScreen() {
    val navController = localNavController.current

    // The Title Bar will be pinned at the top of the screen even scrolled
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        Modifier.background(White)
    ) {
        TopAppBar(
            title = { Text(text = "添加激素数据", style = Typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("track_screen")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "返回"
                    )
                }
            },
            colors = titleBarColors,
            scrollBehavior = scrollBehavior
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)) {
            DetailItem(true, false, "日期") {
                InputButton(
                    true,
                    it,
                    user.startDate?.toString() ?: "点击选择数据日期"
                ) {
                    showDatePicker = true
                }
            }
        }
    }

    if (showDatePicker) {
        StartDatePicker { state -> showDatePicker = state }
    }
}