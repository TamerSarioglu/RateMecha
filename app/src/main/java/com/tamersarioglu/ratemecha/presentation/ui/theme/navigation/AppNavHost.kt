package com.tamersarioglu.ratemecha.presentation.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tamersarioglu.ratemecha.presentation.ui.theme.auth.LoginScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.auth.RegisterScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.home.HomeScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.mechanic.MechanicDetailScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.profile.ProfileScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.review.AddReviewScreen
import com.tamersarioglu.ratemecha.presentation.ui.theme.review.ReviewDetailScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Route.Home.route else Route.Login.route,
        modifier = modifier
    ) {
        authGraph(navController)
        homeGraph(navController)
        mechanicGraph(navController)
        reviewGraph(navController)
        profileGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    composable(Route.Login.route) {
        LoginScreen(
            onNavigateToRegister = { navController.navigate(Route.Register.route) },
            onLoginSuccess = { navController.navigate(Route.Home.route) {
                popUpTo(Route.Login.route) { inclusive = true }
            }}
        )
    }

    composable(Route.Register.route) {
        RegisterScreen(
            onNavigateBack = { navController.popBackStack() },
            onRegisterSuccess = { navController.navigate(Route.Home.route) {
                popUpTo(Route.Register.route) { inclusive = true }
            }}
        )
    }
}

private fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    composable(Route.Home.route) {
        HomeScreen(
            onMechanicClick = { mechanicId ->
                navController.navigate("${Route.MechanicDetail.route}/$mechanicId")
            },
            onProfileClick = { navController.navigate(Route.Profile.route) }
        )
    }
}

private fun NavGraphBuilder.mechanicGraph(navController: NavHostController) {
    composable(
        route = "${Route.MechanicDetail.route}/{mechanicId}",
        arguments = listOf(navArgument("mechanicId") { type = NavType.StringType })
    ) { backStackEntry ->
        val mechanicId = backStackEntry.arguments?.getString("mechanicId") ?: ""
        MechanicDetailScreen(
            mechanicId = mechanicId,
            onNavigateBack = { navController.popBackStack() },
            onAddReviewClick = { navController.navigate("${Route.AddReview.route}/$mechanicId") },
            onReviewClick = { reviewId ->
                navController.navigate("${Route.ReviewDetail.route}/$reviewId")
            }
        )
    }
}

private fun NavGraphBuilder.reviewGraph(navController: NavHostController) {
    composable(
        route = "${Route.AddReview.route}/{mechanicId}",
        arguments = listOf(navArgument("mechanicId") { type = NavType.StringType })
    ) { backStackEntry ->
        val mechanicId = backStackEntry.arguments?.getString("mechanicId") ?: ""
        AddReviewScreen(
            mechanicId = mechanicId,
            onNavigateBack = { navController.popBackStack() },
            onReviewAdded = { navController.popBackStack() }
        )
    }

    composable(
        route = "${Route.ReviewDetail.route}/{reviewId}",
        arguments = listOf(navArgument("reviewId") { type = NavType.StringType })
    ) { backStackEntry ->
        val reviewId = backStackEntry.arguments?.getString("reviewId") ?: ""
        ReviewDetailScreen(
            reviewId = reviewId,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    composable(Route.Profile.route) {
        ProfileScreen(
            onNavigateBack = { navController.popBackStack() },
            onReviewClick = { reviewId ->
                navController.navigate("${Route.ReviewDetail.route}/$reviewId")
            },
            onLogout = {
                navController.navigate(Route.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}