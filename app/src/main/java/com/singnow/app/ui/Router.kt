package com.singnow.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.singnow.app.navController
import com.singnow.app.ui.screens.HomeScreen
import com.singnow.app.ui.screens.MediaPlayerScreen
import com.singnow.app.ui.screens.ProfileScreen
import com.singnow.app.ui.screens.SearchScreen
import com.singnow.app.ui.screens.SettingScreen
import com.singnow.app.ui.screens.auth.LoginScreen
import com.singnow.app.ui.screens.auth.RegisterScreen
import kotlinx.serialization.Serializable

object RouterState {
    var index = mutableIntStateOf(0)
}

sealed class Router {
    @Serializable
    data object HomeScreen : Router()

    @Serializable
    data object SettingScreen : Router()

    @Serializable
    data object ProfileScreen : Router()

    @Serializable
    data object LoginScreen : Router()

    @Serializable
    data object RegisterScreen : Router()

    @Serializable
    data object SearchScreen : Router()

    @Serializable
    data class MediaPlayerScreen(
        val videoKey: String
    ) : Router()
}

@Composable
fun RouterSetup(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Router.HomeScreen) {
        composable<Router.HomeScreen> {
            HomeScreen().Screen()
        }
        composable<Router.SettingScreen> {
            SettingScreen().Screen()
        }
        composable<Router.LoginScreen> {
            LoginScreen().Screen()
        }
        composable<Router.RegisterScreen> {
            RegisterScreen().Screen()
        }
        composable<Router.SearchScreen> {
            SearchScreen().Screen()
        }
        composable<Router.ProfileScreen> {
            ProfileScreen().Screen()
        }
        composable<Router.MediaPlayerScreen> {
            val args = it.toRoute<Router.MediaPlayerScreen>()
            MediaPlayerScreen().Screen(videoKey = args.videoKey)
        }
    }
}

fun Navigate(router: Router) {
    val navController: NavHostController = navController
    when (router) {
        is Router.HomeScreen -> {
            RouterState.index.intValue = 0
        }

        is Router.SettingScreen -> {
            RouterState.index.intValue = 1
        }

        else -> {
        }
    }
    navController.navigate(router)
}

@Composable
fun NavigationBarBottomContainer() {
    val routers = listOf(
        Router.HomeScreen,
        Router.SettingScreen
    )
    val labels = listOf(
        "Home",
        "Setting"
    )
    val icons = listOf(
        Icons.Sharp.Home,
        Icons.Sharp.Settings
    )
    NavigationBar {
        routers.forEachIndexed { index, router ->
            NavigationBarItem(
                selected = RouterState.index.intValue == index,
                label = { Text(text = labels[index]) },
                onClick = {
                    Navigate(router)
                }, icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = null
                    )
                }
            )
        }
    }
}