package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.menuItemColors

@Composable
fun MenuItem(content: String, icon: ImageVector, event: () -> Unit) {
    ListItem(
        headlineContent = { Text(text = content, style = Typography.titleSmall) },
        leadingContent = { Icon(icon, null) },
        modifier = Modifier.clickable { event() },
        colors = menuItemColors
    )
}