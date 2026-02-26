package com.example.rekreativa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val start = if (FirebaseAuth.getInstance().currentUser != null) Routes.Main else Routes.Login
    NavHost(
        navController = navController,
        startDestination = start
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
                onGoToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Main) {
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

            if (!isLoggedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                }
            } else{
                MainScreen(
                    onOpenTerenDetails = { /* todo */ },
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Main) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(Routes.Profil) {
            ProfilScreen(
                onLogout = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                }
            )
        }

    }
}