package br.com.teste.controledevendas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.teste.controledevendas.design.theme.ControleDeVendasTheme
import br.com.teste.controledevendas.navhost.DefaultNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ControleDeVendasTheme {
                DefaultNavHost(
                    navHostController = navController
                )
            }
        }
    }
}
