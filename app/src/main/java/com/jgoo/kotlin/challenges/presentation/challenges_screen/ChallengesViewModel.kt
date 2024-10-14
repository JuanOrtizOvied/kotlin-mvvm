package com.jgoo.kotlin.challenges.presentation.challenges_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgoo.kotlin.challenges.domain.repository.ChallengesRepository
import com.jgoo.kotlin.util.Event
import com.jgoo.kotlin.util.EventBus.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengesViewModel @Inject constructor(
    private val productsRepository: ChallengesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChallengesViewState())
    val state = _state.asStateFlow()

    init {
        Log.d("ChallengesViewModel", "ViewModel initialized")
        getChallenges()
    }

    fun getChallenges() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            productsRepository.getChallenges()
                .onRight { challenges ->
                    Log.d("ChallengesViewModel productsRepository.getChallenges", "Right Side")
                    _state.update {
                        it.copy(challenges = challenges)
                    }
                }.onLeft { error ->
                    Log.e("ChallengesViewModel productsRepository.getChallenges - LEFT SIDE", error.error.message)
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }
}