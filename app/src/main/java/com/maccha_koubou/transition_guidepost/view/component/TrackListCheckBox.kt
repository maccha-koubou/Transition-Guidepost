package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.DataRecord
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.model.MedicationRecord
import com.maccha_koubou.transition_guidepost.model.RecordedData
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime


/**
 * Checkbox will be checked within 5min of checking for easily regret when the user mistouched
 * But it will be unchecked in other conditions so that the user can record new data
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationCheckBoxAndDescription(item: MedicationData) {
    var isChecked by remember { mutableStateOf(false) }
    var onCheckedChange by remember { mutableStateOf(false) }
    var showRegretRecordMenu by remember { mutableStateOf(false) }
    var checkBoxDescription by remember { mutableStateOf("点击选框\n打卡用药") }

    // Update the checkbox and its description text every 0.3s
    LaunchedEffect(item.dataList.lastOrNull()) {
        while (true) {
            isChecked =
                item.dataList.lastOrNull()?.time
                    ?.isAfter(LocalDateTime.now().minusMinutes(5)) // False when the last dose time is before 5min
                    ?: false // False when the list is empty
            checkBoxDescription = when (item.dataList.isEmpty())
            {
                true -> "点击选框\n打卡用药"
                else -> {
                    val totalIntervalHours = Duration.between(item.dataList.last().time, LocalDateTime.now()).toHours()
                    val totalIntervalMinutes = Duration.between(item.dataList.last().time, LocalDateTime.now()).toMinutes()
                    val intervalDays = totalIntervalHours / 24
                    val intervalHours = totalIntervalHours % 24
                    if (totalIntervalHours == 0L) {
                        "上次用药\n${totalIntervalMinutes}分钟前"
                    } else if (intervalDays == 0L) {
                        "上次用药\n${intervalHours}小时前"
                    } else {
                        "上次用药\n${intervalDays}天${intervalHours}小时前"
                    }
                }
            }
            delay(300L)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Description text
        Text(
            text = checkBoxDescription,
            textAlign = TextAlign.End,
            style = Typography.bodySmall
        )

        // Checkbox
        RegrettableCheckBox(
            isChecked, {  },
            { state -> onCheckedChange = state },
            true,
            item)
    }

    // When the checkbox is changed,
    // add new record if the checkbox is unchecked,
    // or show regret menu
    if (onCheckedChange) {
        onCheckedChange = false
        if (isChecked) {
            // Show regret menu
            showRegretRecordMenu = true
        } else {
            //Add new record to the list
            item.addCommonRecord()
            isChecked = true
        }
    }

    // Show regret menu
    if (showRegretRecordMenu) {
        RegretMedicationRecordMenu(item) { state -> showRegretRecordMenu = state }
    }
}