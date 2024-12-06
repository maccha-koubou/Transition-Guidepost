package com.maccha_koubou.transition_guidepost.storage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import com.maccha_koubou.transition_guidepost.model.BodyData
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.model.TestData
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.LightPink
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.White

// Lists of the data in the Track's Data Card
var medicationItemList = mutableListOf<MedicationData>()
var bodyItemList = mutableListOf<BodyData>()
var healthItemList = mutableListOf<TestData>()


// Default data of E2, T and PRL
val e2Unit = mapOf("pmol/L" to 1f, "pg/mL" to 272.38f)
val e2FeminizationRecommendation = 367.1341f .. 734.2683f
val e2MasculinizationRecommendation = 36.71341f .. 183.5670f

val tUnit = mapOf("nmol/L" to 1f, "ng/mL" to 0.28843f, "ng/dL" to 28.843f)
val tFeminizationRecommendation = 0.3467045f .. 1.906875f
val tMasculinizationRecommendation = Pair(14f, 25.4f)

val prlUnit = mapOf("mIU/L" to 1f, "ng/mL" to 0.04717f, "ng/dL" to 4.717f)
val prlFeminizationRecommendation = 101.55f .. 493.96f

val e2Data = TestData("雌二醇", Pink, Icons.Filled.Star, e2Unit, e2FeminizationRecommendation, true, false, null)
val tData = TestData("睾酮", Blue, Icons.Filled.Star, tUnit, tFeminizationRecommendation, true, false, null)