package com.jgoo.kotlin.challenges.presentation.challenges_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jgoo.kotlin.challenges.presentation.challenges_screen.components.ChallengeCard
import com.jgoo.kotlin.challenges.presentation.util.components.CustomTopAppBar
import com.jgoo.kotlin.challenges.presentation.util.components.LoadingDialog

@Composable
internal fun ChallengesScreen() {
    val viewModel: ChallengesViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ChallengesContent(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengesContent(
    state: ChallengesViewState
) {
    LoadingDialog(isLoading = state.isLoading)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(text = "Desafios") }
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp
        ) {
            items(state.challenges) { challenge ->
                ChallengeCard(challenge = challenge)
            }
        }
    }
}



