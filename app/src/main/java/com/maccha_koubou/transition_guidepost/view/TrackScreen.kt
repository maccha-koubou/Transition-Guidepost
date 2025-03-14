package com.maccha_koubou.transition_guidepost.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.storage.chartSetting
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.BackgroundGray

@SuppressLint("NewApi", "UnrememberedMutableState")
@Composable
fun TrackScreen() {
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .background(BackgroundGray)
                .padding(16.dp, 12.dp, 16.dp, 24.dp)
                .weight(1f)
        ) {
            DurationBanner()

            Spacer(modifier = Modifier.height(16.dp))

            Column(Modifier.weight(1f)) {
                HormoneCard(mutableStateOf(chartSetting().chartSetting.value.isHormoneEmpty))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(Modifier.weight(1f)){
                DataCard()
            }
        }
    }
}