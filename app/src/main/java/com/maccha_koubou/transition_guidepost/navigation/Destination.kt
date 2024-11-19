package com.maccha_koubou.transition_guidepost.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object TrackScreen : NavDestination("数据", "track_screen", Icons.Filled.Share)
    object CollectionScreen : NavDestination("收藏", "collection_screen", Icons.Filled.Star)
}

