package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.DataRecord
import com.maccha_koubou.transition_guidepost.model.MedicationRecord
import com.maccha_koubou.transition_guidepost.model.RecordedData
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.CheckBoxCheckedColors
import com.maccha_koubou.transition_guidepost.ui.theme.CheckBoxUncheckedColors
import com.maccha_koubou.transition_guidepost.ui.theme.CheckBoxUncheckedMedicationColors
import com.maccha_koubou.transition_guidepost.ui.theme.Gray

/**
 * This checkbox is used in the items of the Data Card's lists except the cycle items
 * Its unchecked version provides 2 looks:
 *      the empty checkbox is for medication items,
 *      the checkbox with "+" is for others
 */
@Composable
fun RegrettableCheckBox(
    isChecked: Boolean, state: (Boolean) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isMedication: Boolean,
    item: RecordedData<out DataRecord>
) {
    var checkBoxColor = when (isChecked) {
        true -> CheckBoxCheckedColors
        false -> when (isMedication) {
            true -> CheckBoxUncheckedMedicationColors
            false -> CheckBoxUncheckedColors
        }
    }
    var checkBoxIcon = when (isChecked) {
        true -> Icons.Filled.Check
        false -> Icons.Filled.Add
    }
    var checkBoxDescription = when (isChecked) {
        true -> "修改数据"
        false -> "添加数据"
    }
    IconButton(
        onClick = {
            state(!isChecked)
            onCheckedChange(true)
        },
        modifier = Modifier
            .size(48.dp)
            .padding(12.dp),
        colors = checkBoxColor
    ) {
        Icon(
            checkBoxIcon,
            checkBoxDescription,
            Modifier.border(2.dp, Gray, RoundedCornerShape(12.dp))
        )
    }
}