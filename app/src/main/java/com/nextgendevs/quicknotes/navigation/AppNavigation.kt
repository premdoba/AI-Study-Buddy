package com.nextgendevs.quicknotes.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.nextgendevs.quicknotes.settings.SettingsViewModel
import com.nextgendevs.quicknotes.ui.screens.AiDisclaimerScreen
import com.nextgendevs.quicknotes.ui.screens.ContactUsScreen
import com.nextgendevs.quicknotes.ui.screens.DownloadsScreen
import com.nextgendevs.quicknotes.viewmodel.StudyViewModel
import com.nextgendevs.quicknotes.viewmodel.TodoViewModel
import com.nextgendevs.quicknotes.ui.screens.GenerateScreen
import com.nextgendevs.quicknotes.ui.screens.HistoryDetailScreen
import com.nextgendevs.quicknotes.ui.screens.HistoryScreen
import com.nextgendevs.quicknotes.ui.screens.LoginTestScreen
import com.nextgendevs.quicknotes.ui.screens.McqDetailScreen
import com.nextgendevs.quicknotes.ui.screens.McqsScreen
import com.nextgendevs.quicknotes.ui.screens.QuickTodoScreen
import com.nextgendevs.quicknotes.ui.screens.QuizScreen
import com.nextgendevs.quicknotes.ui.screens.SettingsScreen
import com.nextgendevs.quicknotes.ui.screens.SignupScreen
import com.nextgendevs.quicknotes.ui.screens.TermsConditionsScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Routes(val route: String) {
    object Generate : Routes("generate")
    object Downloads : Routes("downloads")
    object History : Routes("history")
    object Mcqs : Routes("mcqs")
    object Contact : Routes("contact")
    object Signup : Routes("signup")
    object Terms : Routes("terms")
    object AiDisclaimer : Routes("disclaimer")
    object QuickTodo : Routes("todo")
    object Login : Routes ("login")
    object McqDetail: Routes("mcq_detail/{id}") {
        fun createRoute(id: Int) = "mcq_detail/$id"
    }
    object HistoryDetail : Routes("historyDetail/{id}") {
        fun createRoute(id: Int) = "historyDetail/$id"
    }
    object Quiz : Routes("quiz")
    object Settings : Routes("settings")
}

@Composable
fun AppNavigation(navController: NavHostController, vm: StudyViewModel, todoVM: TodoViewModel, settinsVM: SettingsViewModel) {
    NavHost(navController = navController,
        startDestination =
        if (FirebaseAuth.getInstance().currentUser != null)
            Routes.Generate.route
        else
            Routes.Login.route
    )
    {

        composable(Routes.Generate.route) {
            GenerateScreen(navController, vm, settinsVM)
        }

        composable(Routes.Login.route) {
            LoginTestScreen(navController)
        }

        composable(Routes.Signup.route) {
            SignupScreen(navController)
        }

        composable(Routes.Quiz.route) {
            QuizScreen(navController, vm)
        }

        composable(Routes.Downloads.route) {
            DownloadsScreen(navController)
        }

        composable(Routes.QuickTodo.route) {
            QuickTodoScreen(navController, todoVM)
        }

        composable(Routes.Mcqs.route) {
            McqsScreen(navController, vm)
        }

        composable(
            Routes.McqDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            McqDetailScreen(navController, vm, id)
        }

        composable(Routes.History.route) {
            HistoryScreen(navController, vm)
        }

        composable(Routes.Contact.route) {
            ContactUsScreen(navController)
        }
        composable(Routes.Settings.route) {
            SettingsScreen(navController, settinsVM, vm)
        }

        composable(Routes.AiDisclaimer.route) {
            AiDisclaimerScreen(navController)
        }

        composable(Routes.Terms.route) {
            TermsConditionsScreen(navController)
        }

        composable(
            route = Routes.HistoryDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            HistoryDetailScreen(navController, vm, id)
        }
    }
}