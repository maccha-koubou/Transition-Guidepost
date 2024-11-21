package com.maccha_koubou.transition_guidepost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import com.maccha_koubou.transition_guidepost.model.Dosage
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.storage.medicationItemList
import com.maccha_koubou.transition_guidepost.ui.theme.LightBlue
import com.maccha_koubou.transition_guidepost.ui.theme.LightPink

fun addTestData() {
    medicationItemList.apply {
        add(MedicationData("补佳乐", LightPink, Icons.Filled.Refresh, Dosage(1f, "mg", 1, 3), true, false, null))
        add(MedicationData("色普龙", LightBlue, Icons.Filled.Info, Dosage(12.5f, "mg", 2,1), true, false, null))
    }
}