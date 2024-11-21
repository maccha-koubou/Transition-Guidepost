package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.DarkPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.LightPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.largeInputButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.largeMainButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.largeSecondaryButtonColors

@Composable
fun MainButton(isFill: Boolean, text: String, event: () -> Unit) {
    Button(
        onClick = { event() },
        colors = largeMainButtonColors,
        modifier = if (isFill) Modifier.fillMaxWidth() else Modifier
    ) {
        Text(
            text = text,
            style = Typography.labelLarge,
            color = White
        )
    }
}

@Composable
fun MainButton(isFill: Boolean, text: String, icon: ImageVector, event: () -> Unit) {
    Button(
        onClick = { event() },
        colors = largeMainButtonColors,
        modifier = if (isFill) Modifier.fillMaxWidth() else Modifier
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = text,
            style = Typography.labelLarge,
            color = White
        )
    }
}

@Composable
fun SecondaryButton(isFill: Boolean, text: String, event: () -> Unit) {
    Button(
        onClick = { event() },
        colors = largeSecondaryButtonColors,
        modifier = if (isFill) Modifier.fillMaxWidth() else Modifier,
        border = BorderStroke(1.dp, LightPurple)
    ) {
        Text(
            text = text,
            style = Typography.labelLarge,
            color = Gray
        )
    }
}

@Composable
fun InputButton(isFill: Boolean, isAvailable: Boolean, text: String, event: () -> Unit) {
    Button(
        onClick = { event() },
        colors = largeInputButtonColors,
        enabled = isAvailable,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .then(if (isFill) Modifier.fillMaxWidth() else Modifier)
            .then(if (!isAvailable) Modifier.border(1.dp, Gray, RoundedCornerShape(18.dp)) else Modifier)
            .height(36.dp),
        // Make sure that the text is right at the center of the button
        contentPadding = PaddingValues(vertical = 0.dp)
    ) {
        Text(
            text = text,
            style = Typography.titleSmall,
            color = if (isAvailable) DarkPurple else Gray
        )
    }
}