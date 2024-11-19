package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.*
import com.maccha_koubou.transition_guidepost.view.component.BottomTabRow


// Empty card with tab bar
@Preview
@Composable
fun DataCard() {
    var dataTypeState by remember { mutableStateOf(0) }
    val dataTabTitles = listOf("用药", "生活", "健康")
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
            when (dataTypeState) {
                0 -> MedicationCard()
                1 -> BodyCard()
                2 -> HealthCard()
            }
        }
        HorizontalDivider(thickness = 1.dp, color = LightPurple)
        BottomTabRow(
            dataTypeState,
            dataTabTitles,
            { newState -> dataTypeState = newState }
        )
    }
}