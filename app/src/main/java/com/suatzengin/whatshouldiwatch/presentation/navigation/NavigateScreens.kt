package com.suatzengin.whatshouldiwatch.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suatzengin.whatshouldiwatch.presentation.detail.MovieDetailScreen
import com.suatzengin.whatshouldiwatch.presentation.genres.GenresScreen
import com.suatzengin.whatshouldiwatch.presentation.home.HomeScreen
import com.suatzengin.whatshouldiwatch.presentation.movie_genres.MovieWithGenres
import com.suatzengin.whatshouldiwatch.presentation.popular.PopularMoviesScreen
import com.suatzengin.whatshouldiwatch.presentation.search.SearchScreen
import com.suatzengin.whatshouldiwatch.presentation.top_rated.TopRatedScreen
import com.suatzengin.whatshouldiwatch.presentation.watch_list.WatchListScreen

@Composable
fun NavigateScreens(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.TopRated.route) {
            TopRatedScreen(navController = navController)
        }
        composable(Screen.Popular.route) {
            PopularMoviesScreen(navController = navController)
        }
        composable(Screen.MovieDetail.route + "/{movie_id}") {
            MovieDetailScreen(navController = navController)
        }
        composable(Screen.Genres.route) {
            GenresScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.WatchList.route) {
            WatchListScreen()
        }
        composable(
            Screen.MovieWithGenres.route + "/{genreId}/{genreName}",
            arguments = listOf(
                navArgument("genreId") { type = NavType.IntType },
                navArgument("genreName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getInt("genreId")
            val genreName = backStackEntry.arguments?.getString("genreName")
            MovieWithGenres(navController = navController, genreId = genreId, genreName = genreName)
        }
    }
}