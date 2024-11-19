package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maccha_koubou.transition_guidepost.addTestData
import com.maccha_koubou.transition_guidepost.navigation.NavDestination
import com.maccha_koubou.transition_guidepost.ui.theme.Black
import com.maccha_koubou.transition_guidepost.ui.theme.DarkPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.navigationBarColors


@Preview
@Composable
fun MainPage() {
    val navController = rememberNavController()
    addTestData()
    // List of main screens
    val screens = listOf(
        NavDestination.TrackScreen,
        NavDestination.CollectionScreen
    )

    // Navigation bar
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(
                            imageVector = screen.icon,
                            contentDescription = null
                            //modifier = Modifier.size(32.dp)
                        ) },
                        label = { Text(
                            text = screen.title,
                            style = Typography.labelMedium
                        ) },
                        colors = navigationBarColors,
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "track_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NavDestination.TrackScreen.route) {
                TrackScreen()
            }
            composable(route = NavDestination.CollectionScreen.route) {
                CollectionScreen()
            }
        }
    }
}