package com.example.datingapp.ui.screens.dating

import com.example.datingapp.domain.model.SwipeDirection
import com.example.datingapp.domain.model.UserProfile

/**
 * UI State thuần túy cho màn hình Dating.
 * Tách riêng khỏi ViewModel để đảm bảo tính bất biến (Immutability) và dễ kiểm thử.
 */
data class DatingUiState(
    val profiles: List<UserProfile> = emptyList(),
    val likedProfiles: List<UserProfile> = emptyList(),
    val passedProfiles: List<UserProfile> = emptyList(),
    val lastAction: SwipeDirection? = null,
    val showMatchDialog: Boolean = false,
    val matchedProfile: UserProfile? = null
)
