package com.suatzengin.whatshouldiwatch.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.suatzengin.whatshouldiwatch.domain.model.HomeType

import com.suatzengin.whatshouldiwatch.presentation.genres.GenresViewModel
import com.suatzengin.whatshouldiwatch.presentation.home.components.Header
import com.suatzengin.whatshouldiwatch.presentation.home.components.PopularHomeItem
import com.suatzengin.whatshouldiwatch.presentation.home.components.TopRatedHomeItem
import com.suatzengin.whatshouldiwatch.presentation.navigation.Screen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    genresViewModel: GenresViewModel = hiltViewModel(),
    navController: NavController
) {

    val genres = genresViewModel.stateGenres.value

    val state = viewModel.state.value
    val homeItems = state.homeList


    Column(
        modifier = Modifier
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {

            homeItems.forEach { homeType ->
                when (homeType) {
                    is HomeType.TopRated -> {
                        item {
                            Header(header = "Top Rated", onClickSeeMore = {
                                navController.navigate(Screen.TopRated.route)
                            })
                        }
                        item {
                            LazyRow {
                                items(homeType.topRated) { topRated ->
                                    TopRatedHomeItem(topRated = topRated) { navigatedItem ->
                                        navController.navigate(Screen.MovieDetail.route + "/${navigatedItem.id}")
                                    }
                                }
                            }
                        }
                    }
                    is HomeType.Popular -> {
                        item {
                            Header(header = "Popular", onClickSeeMore = {
                                navController.navigate(Screen.Popular.route)
                            })
                        }
                        items(items = homeType.popular) { popular ->
                            PopularHomeItem(popular = popular, genres = genres) { navigatedItem ->
                                navController.navigate(Screen.MovieDetail.route + "/${navigatedItem.id}")
                            }
                        }
                    }
                }
            }

        }
        if (state.error.isNotBlank()) {
            Text(text = "${state.error} ds")
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}

