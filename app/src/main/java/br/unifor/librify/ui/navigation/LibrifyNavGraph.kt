package br.unifor.librify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.unifor.librify.ui.features.auth.login.LoginScreen
import br.unifor.librify.ui.features.auth.register.RegisterScreen
import br.unifor.librify.ui.features.auth.forgotpassword.ForgotPasswordScreen
import br.unifor.librify.ui.features.bookdetail.BookDetailScreen
import br.unifor.librify.ui.features.chat.ChatScreen
import br.unifor.librify.ui.features.admin.AdminPanelScreen

object LibrifyDestinations {
    const val AUTH_ROUTE = "auth_graph"
    const val MAIN_ROUTE = "main_graph"
    
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"
    
    const val HOME_ROUTE = "home"
    const val CATALOG_ROUTE = "catalog"
    const val HISTORY_ROUTE = "history"
    const val PUBLISH_ROUTE = "publish"
    const val PROFILE_ROUTE = "profile"
    
    const val BOOK_DETAIL_ROUTE = "book_detail/{bookId}"
    const val CHAT_ROUTE = "chat_assistant"
    const val ADMIN_PANEL_ROUTE = "admin_panel"
}

@Composable
fun LibrifyNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LibrifyDestinations.AUTH_ROUTE,
        modifier = modifier
    ) {
        // Authentication Graph
        composable(LibrifyDestinations.AUTH_ROUTE) {
            LoginScreen(
                onRegisterClick = { navController.navigate(LibrifyDestinations.REGISTER_ROUTE) },
                onForgotPasswordClick = { navController.navigate(LibrifyDestinations.FORGOT_PASSWORD_ROUTE) },
                onLoginSuccess = {
                    navController.navigate(LibrifyDestinations.MAIN_ROUTE) {
                        popUpTo(LibrifyDestinations.AUTH_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(LibrifyDestinations.REGISTER_ROUTE) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(LibrifyDestinations.MAIN_ROUTE) {
                        popUpTo(LibrifyDestinations.AUTH_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(LibrifyDestinations.FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onSendSuccess = { navController.popBackStack() }
            )
        }

        // Main App Content (with Bottom Bar)
        composable(LibrifyDestinations.MAIN_ROUTE) {
            MainScreen(
                onBookClick = { bookId -> 
                    navController.navigate("book_detail/$bookId")
                },
                onChatClick = {
                    navController.navigate(LibrifyDestinations.CHAT_ROUTE)
                },
                onAdminClick = {
                    navController.navigate(LibrifyDestinations.ADMIN_PANEL_ROUTE)
                },
                onLogout = {
                    navController.navigate(LibrifyDestinations.AUTH_ROUTE) {
                        popUpTo(LibrifyDestinations.MAIN_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        // Book Details
        composable(
            route = LibrifyDestinations.BOOK_DETAIL_ROUTE,
            arguments = listOf(navArgument("bookId") { type = androidx.navigation.NavType.StringType })
        ) {
            BookDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Chat Assistant
        composable(LibrifyDestinations.CHAT_ROUTE) {
            ChatScreen(onBackClick = { navController.popBackStack() })
        }

        // Admin Panel
        composable(LibrifyDestinations.ADMIN_PANEL_ROUTE) {
            AdminPanelScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
