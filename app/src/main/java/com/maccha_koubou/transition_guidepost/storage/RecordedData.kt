package com.maccha_koubou.transition_guidepost.storage

import com.maccha_koubou.transition_guidepost.model.TestData

// Default data of E2, T and PRL
val e2Unit = mapOf("pmol/L" to 1f, "pg/mL" to 272.38f)
val e2FeminizationRecommendation = Pair(367.1341f, 734.2683f)
val e2MasculinizationRecommendation = Pair(36.71341f, 183.5670f)

val tUnit = mapOf("nmol/L" to 1f, "ng/mL" to 0.28843f, "ng/dL" to 28.843f)
val tFeminizationRecommendation = Pair(0.3467045f, 1.906875f)
val tMasculinizationRecommendation = Pair(14f, 25.4f)

val prlUnit = mapOf("mIU/L" to 1f, "ng/mL" to 0.04717f, "ng/dL" to 4.717f)
val prlFeminizationRecommendation = Pair(101.55f, 493.96f)

val e2Data = TestData("雌二醇", e2Unit, e2FeminizationRecommendation, true, null)
val tData = TestData("睾酮", tUnit, tFeminizationRecommendation, true, null)