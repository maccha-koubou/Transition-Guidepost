package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
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

@Composable
fun DetailItem(isAvailable: Boolean, isTitleBar: Boolean, title: String, content: @Composable () -> Unit) {
    // Use bold style if this item is used in title bar
    var titleStyle =
        if (isTitleBar) {
            Typography.titleLarge
        } else {
            Typography.titleSmall
        }
    // Use gray color if this item is unavailable
    if (!isAvailable) titleStyle = titleStyle.copy(color = Gray)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
    ) {
        // Title
        Row(
            modifier = Modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = titleStyle
            )
        }
        // Detail buttons
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            content()
        }
    }
}