package com.suatzengin.whatshouldiwatch.presentation.navigation

sealed class Screen(val route: String){
    object Home: Screen(route = "home")
    object TopRated: Screen(route = "top_rated")
    object Popular: Screen(route = "popular")
    object MovieDetail: Screen(route = "movie_detail")
    object Genres: Screen(route = "genres")
    object Search: Screen(route = "search")
    object WatchList: Screen(route = "watchlist")
    object MovieWithGenres: Screen(route = "movie_with_genres")
}
