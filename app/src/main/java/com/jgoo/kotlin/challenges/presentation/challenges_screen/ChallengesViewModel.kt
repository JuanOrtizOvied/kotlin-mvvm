package com.jgoo.kotlin.challenges.presentation.challenges_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.FirebaseFirestore
import com.jgoo.kotlin.challenges.domain.model.Challenge
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

    private val firestore = FirebaseFirestore.getInstance()
    private var challengesListener: ListenerRegistration? = null

    private val _state = MutableStateFlow(ChallengesViewState())
    val state = _state.asStateFlow()

    init {
        Log.d("ChallengesViewModel", "ViewModel initialized")
        getChallengesFromAPI()
        listenForFirestoreChanges()
    }

    // Fetch initial challenges from the remote API
    private fun getChallengesFromAPI() {
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

    // Listen for real-time changes in Firestore
    private fun listenForFirestoreChanges() {
        challengesListener = firestore.collection("challenges")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("ChallengesViewModel", "Firestore listener error", exception)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val updatedChallenges = _state.value.challenges.toMutableList()

                    snapshot.documents.forEach { document ->
                        val uuid = document.getString("uuid")
                        val newDescription = document.getString("description")

                        // Find the existing challenge and update only its description
                        updatedChallenges.find { it.uuid == uuid }?.let { existingChallenge ->
                            if (newDescription != null) {
                                val updatedChallenge = existingChallenge.copy(description = newDescription)
                                val index = updatedChallenges.indexOf(existingChallenge)
                                updatedChallenges[index] = updatedChallenge
                            }
                        }
                    }

                    Log.d("ChallengesViewModel", "Firestore updated challenges with new descriptions: $updatedChallenges")
                    _state.update {
                        it.copy(challenges = updatedChallenges)
                    }
                }
            }
    }


    // Remove Firestore listener
    private fun removeChallengesListener() {
        challengesListener?.remove()
    }

    override fun onCleared() {
        super.onCleared()
        removeChallengesListener()
    }
}
