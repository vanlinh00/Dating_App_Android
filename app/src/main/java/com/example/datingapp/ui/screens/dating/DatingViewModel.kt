package com.example.datingapp.ui.screens.dating

import androidx.lifecycle.ViewModel
import com.example.datingapp.data.repository.DatingRepository
import com.example.datingapp.data.repository.DatingRepositoryImpl
import com.example.datingapp.domain.model.SwipeDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel chịu trách nhiệm quản lý trạng thái hiển thị và hành vi của người dùng trên DatingScreen.
 * Nhận DatingRepository qua constructor injection để dễ dàng Mock trong Unit Test.
 */
class DatingViewModel(
    private val repository: DatingRepository = DatingRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DatingUiState(profiles = repository.getProfiles())
    )
    val uiState: StateFlow<DatingUiState> = _uiState.asStateFlow()

    /**
     * Bỏ qua hồ sơ hiện tại (Swipe Left / Pass).
     */
    fun onSwipeLeft() {
        val currentList = _uiState.value.profiles
        if (currentList.isEmpty()) return

        val topProfile = currentList.first()
        val remaining = currentList.drop(1)

        _uiState.update { state ->
            state.copy(
                profiles = remaining,
                passedProfiles = state.passedProfiles + topProfile,
                lastAction = SwipeDirection.LEFT
            )
        }
    }

    /**
     * Thích hồ sơ hiện tại (Swipe Right / Like).
     */
    fun onSwipeRight() {
        val currentList = _uiState.value.profiles
        if (currentList.isEmpty()) return

        val topProfile = currentList.first()
        val remaining = currentList.drop(1)
        val isMatch = repository.isMatch(topProfile)

        _uiState.update { state ->
            state.copy(
                profiles = remaining,
                likedProfiles = state.likedProfiles + topProfile,
                lastAction = SwipeDirection.RIGHT,
                showMatchDialog = isMatch,
                matchedProfile = if (isMatch) topProfile else null
            )
        }
    }

    /**
     * Siêu thích hồ sơ hiện tại (Swipe Up / Super Like).
     */
    fun onSuperLike() {
        val currentList = _uiState.value.profiles
        if (currentList.isEmpty()) return

        val topProfile = currentList.first()
        val remaining = currentList.drop(1)

        _uiState.update { state ->
            state.copy(
                profiles = remaining,
                likedProfiles = state.likedProfiles + topProfile,
                lastAction = SwipeDirection.UP,
                showMatchDialog = true,
                matchedProfile = topProfile
            )
        }
    }

    /**
     * Đóng hộp thoại ghép đôi thành công.
     */
    fun dismissMatchDialog() {
        _uiState.update { state ->
            state.copy(showMatchDialog = false, matchedProfile = null)
        }
    }

    /**
     * Hoàn tác lượt quẹt thẻ trước đó.
     */
    fun undoLastSwipe() {
        val lastPassed = _uiState.value.passedProfiles.lastOrNull()
        val lastLiked = _uiState.value.likedProfiles.lastOrNull()

        if (lastPassed != null && (_uiState.value.lastAction == SwipeDirection.LEFT || lastLiked == null)) {
            _uiState.update { state ->
                state.copy(
                    profiles = listOf(lastPassed) + state.profiles,
                    passedProfiles = state.passedProfiles.dropLast(1),
                    lastAction = null
                )
            }
        } else if (lastLiked != null) {
            _uiState.update { state ->
                state.copy(
                    profiles = listOf(lastLiked) + state.profiles,
                    likedProfiles = state.likedProfiles.dropLast(1),
                    lastAction = null
                )
            }
        }
    }

    /**
     * Tải lại danh sách hồ sơ khi người dùng đã xem hết.
     */
    fun reloadProfiles() {
        _uiState.update {
            DatingUiState(
                profiles = repository.getProfiles(),
                likedProfiles = emptyList(),
                passedProfiles = emptyList(),
                lastAction = null,
                showMatchDialog = false,
                matchedProfile = null
            )
        }
    }
}
