package com.example.rekreativa

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ){
        composable(Routes.Splash){
            SplashScreen(
                onGoToLogin = {
                    navController.navigate(Routes.Login){
                        popUpTo(Routes.Splash){ inclusive = true}
                    }
                }
            )
        }

        composable(Routes.Login){
            LoginScreen(
                onGoToRegister = { navController.navigate(Routes.Register)},
                onLoginSucces = {
                    navController.navigate(Routes.Main){
                        popUpTo(Routes.Login){ inclusive = true}
                    }
                }
            )
        }

        composable(Routes.Register){
            RegisterScreen(
                onGoToLogin = { navController.popBackStack()}
            )
        }

        composable(Routes.Main) {
            MainScreen(
                onOpenTerenDetails = { /* todo */ }
            )
        }

    }
}