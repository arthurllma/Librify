package br.unifor.librify.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.unifor.librify.ui.features.catalog.CatalogScreen
import br.unifor.librify.ui.features.history.HistoryScreen
import br.unifor.librify.ui.features.home.HomeScreen
import br.unifor.librify.ui.features.profile.ProfileScreen
import br.unifor.librify.ui.features.publish.PublishScreen

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem(LibrifyDestinations.HOME_ROUTE, "Home", Icons.Default.Home)
    object Catalog : BottomNavItem(LibrifyDestinations.CATALOG_ROUTE, "Catálogo", Icons.AutoMirrored.Filled.MenuBook)
    object History : BottomNavItem(LibrifyDestinations.HISTORY_ROUTE, "Histórico", Icons.Default.History)
    object Publish : BottomNavItem(LibrifyDestinations.PUBLISH_ROUTE, "Publicar", Icons.Default.AddCircle)
    object Profile : BottomNavItem(LibrifyDestinations.PROFILE_ROUTE, "Perfil", Icons.Default.Person)
}

@Composable
fun MainScreen(
    onBookClick: (String) -> Unit,
    onChatClick: () -> Unit,
    onAdminClick: () -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Catalog,
        BottomNavItem.History,
        BottomNavItem.Publish,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
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
            startDestination = LibrifyDestinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(LibrifyDestinations.HOME_ROUTE) { 
                HomeScreen(onBookClick = onBookClick) 
            }
            composable(LibrifyDestinations.CATALOG_ROUTE) { 
                CatalogScreen(onBookClick = onBookClick) 
            }
            composable(LibrifyDestinations.HISTORY_ROUTE) { HistoryScreen() }
            composable(LibrifyDestinations.PUBLISH_ROUTE) { PublishScreen() }
            composable(LibrifyDestinations.PROFILE_ROUTE) { 
                ProfileScreen(
                    onChatClick = onChatClick,
                    onAdminClick = onAdminClick,
                    onLogout = onLogout
                )
            }
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = name, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
