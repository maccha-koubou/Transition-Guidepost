package com.maccha_koubou.transition_guidepost.view.component

import android.widget.CheckBox
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.addTestData
import com.maccha_koubou.transition_guidepost.model.BodyData
import com.maccha_koubou.transition_guidepost.model.BreastData
import com.maccha_koubou.transition_guidepost.model.CycleData
import com.maccha_koubou.transition_guidepost.model.DataRecord
import com.maccha_koubou.transition_guidepost.model.Dosage
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.model.RecordedData
import com.maccha_koubou.transition_guidepost.model.TestData
import com.maccha_koubou.transition_guidepost.model.TimePointData
import com.maccha_koubou.transition_guidepost.storage.medicationItemList
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import java.text.DecimalFormat

@Composable
fun TrackListItem(item: RecordedData<out DataRecord>) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(White)
            .padding(16.dp, 8.dp, 4.dp, 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            // Icon of this item
            Box(
                Modifier
                    .clickable { /* Edit this item */ }
                    .size(40.dp)
                    .background(item.color, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(item.icon, null)
            }

            // Main information of this item
            Column(
                Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(style = Typography.titleSmall, text = item.name)
                Text(
                    style = Typography.bodySmall,
                    text = descriptionTextOfItem(item)
                )
            }
        }

        // Select the responding check box type based on the type of the item
        Row() {
            when (item) {
                is MedicationData -> {
                    MedicationCheckBoxAndDescription(item)
                }
                is TestData, is BreastData -> {}
                is CycleData -> {}
                is TimePointData -> {}
                else -> {}
            }
        }
    }
}

/**
 * Generate the description text based on the type of the item
  */
fun descriptionTextOfItem(item: RecordedData<out DataRecord>): String {
    when (item) {
        is MedicationData -> {
            val dosage = DecimalFormat("#.##").format(item.currentDosage.dosage)
            val days = if (item.currentDosage.interval == 1) "" else item.currentDosage.interval.toString()
            return "$dosage${item.currentDosage.unitWithoutInterval}/${days}天"
        }
        is TestData -> return ""
        is CycleData -> return ""
        is TimePointData -> return ""
        is BreastData -> return ""
        else -> return ""
    }
}