package br.com.teste.controledevendas.navhost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.teste.controledevendas.order.feature.addorder.view.AddOrderScreen
import br.com.teste.controledevendas.order.feature.detail.view.OrderDetailScreen
import br.com.teste.controledevendas.order.feature.home.view.HomeScreen


@Composable
fun DefaultNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "order") {
        composable("order") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                HomeScreen(
                    navController = navHostController
                )
            }
        }
        composable(
            route = "order/detail/{orderId}",
            arguments = listOf(
                navArgument("orderId") { type = NavType.LongType }
            )
        ) {
            it.arguments?.let { arg ->
                Surface(color = MaterialTheme.colors.background) {
                    OrderDetailScreen(
                        navController = navHostController,
                        orderId = arg.getLong("orderId")
                    )
                }
            }
        }
        composable("order/add") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                AddOrderScreen(
                    navController = navHostController
                )
            }
        }
    }
}