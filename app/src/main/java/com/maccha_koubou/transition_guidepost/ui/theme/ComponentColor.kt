package com.maccha_koubou.transition_guidepost.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.ui.graphics.Color

val IconButtonColors = IconButtonColors(Color.Transparent, Gray, White, White)
val AddButtonColors = IconButtonColors(LightPurple, Purple, LightPurple, White)
val largeMainButtonColors = ButtonColors(DarkPurple, White, Gray, White)
val largeSecondaryButtonColors = ButtonColors(Color.Transparent, Gray, Color.Transparent, BackgroundGray)

val CheckBoxUncheckedColors = IconButtonColors(White, Gray, White, BackgroundGray)
val CheckBoxUncheckedMedicationColors = IconButtonColors(White, Color.Transparent, White, BackgroundGray)
val CheckBoxCheckedColors = IconButtonColors(Gray, White, BackgroundGray, White)
val CheckBoxDuringCycleColors = IconButtonColors(DarkPurple, White, LightPurple, White)

val cardColors = CardColors(White, Black, Gray, Gray)

val tabActiveIndicatorColors = CardColors(DarkPurple, Color.Transparent, Color.Transparent, Color.Transparent)
val tabInactiveIndicatorColors = CardColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent)

val navigationBarColors = NavigationBarItemColors(DarkPurple, DarkPurple, Color.Transparent, Gray, Gray, Gray, Gray)

val menuItemColors = ListItemColors(White, Black, DarkPurple, Gray, Gray, Gray, Gray, Gray, Gray)