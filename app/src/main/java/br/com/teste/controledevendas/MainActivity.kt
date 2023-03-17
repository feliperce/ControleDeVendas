package br.com.teste.controledevendas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.teste.controledevendas.design.theme.ControleDeVendasTheme
import br.com.teste.controledevendas.home.feature.home.view.HomeScreen
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
