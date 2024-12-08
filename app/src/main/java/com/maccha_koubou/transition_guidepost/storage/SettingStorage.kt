package com.maccha_koubou.transition_guidepost.storage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.maccha_koubou.transition_guidepost.model.ChartSetting


// Track settings
class ChartSettingWrapper {
    val chartSetting = mutableStateOf(
        ChartSetting(
            { e2Data },
            { tData },
            { medicationItemList[0] },
            { null },
            { null },
            { null },
            { null },
            { null }
        )
    )
}

@Composable
fun chartSetting(): ChartSettingWrapper {
    val store by remember { mutableStateOf(ChartSettingWrapper()) }
    return store
}