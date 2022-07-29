package com.suatzengin.whatshouldiwatch.presentation.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun AppBottomNavigation(
    navController: NavController,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = Color.White
    ) {

        val items = listOf(
            Screen.Home.route to Icons.Filled.Home,
            Screen.Search.route to Icons.Filled.Search,
            Screen.Genres.route to Icons.Filled.Category,
            Screen.WatchList.route to Icons.Filled.VideoLibrary,
        )

        items.forEach { pair: Pair<String, ImageVector> ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == pair.first } == true,
                icon = { Icon(imageVector = pair.second, contentDescription = "") },
                label = { Text(text = pair.first.uppercase()) },
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(pair.first) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = false
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}
