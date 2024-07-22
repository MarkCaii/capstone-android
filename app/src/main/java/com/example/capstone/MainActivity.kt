package com.example.capstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstone.network.DataService
import com.example.capstone.ui.AppBar
import com.example.capstone.ui.DataScreen
import com.example.capstone.ui.DrawerBody
import com.example.capstone.ui.DrawerHeader
import com.example.capstone.ui.MainScreen
import com.example.capstone.ui.theme.CapstoneTheme
import com.example.capstone.viewmodel.DataViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val dataViewModel: DataViewModel = viewModel()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    },
                    drawerContent = {
                        // Your drawer content here
//                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Home screen",
                                    icon = Icons.Default.Home,
                                ),
                                MenuItem(
                                    id = "data",
                                    title = "Data",
                                    contentDescription = "Data screen",
                                    icon = Icons.Default.List,
                                ),
                            ),
                            onItemClick = { item ->
                                navController.navigate(item.id)
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        )
                    },
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.HOME,
                        modifier = Modifier.padding(it),
                    ) {
                        composable(AppRoutes.HOME) { MainScreen() }
                        composable(AppRoutes.DATA) {
                            DataScreen(dataViewModel = dataViewModel)
                        }
                    }
                }
            }
        }
    }
}
