package com.example.matchcredit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.matchcredit.navigation.AppNavigation
import com.example.matchcredit.ui.theme.MatchCreditTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatchCreditApp()
        }
    }
}

@Composable
fun MatchCreditApp() {

    val navController = rememberNavController()

    MatchCreditTheme {
        Surface {
            AppNavigation(navController)
        }
    }
}