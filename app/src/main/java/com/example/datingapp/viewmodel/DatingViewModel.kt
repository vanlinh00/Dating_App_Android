package com.example.datingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.datingapp.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class SwipeDirection {
    LEFT,   // Bỏ qua (Pass)
    RIGHT,  // Thích (Like)
    UP      // Siêu thích (Superlike)
}

data class DatingUiState(
    val profiles: List<UserProfile> = emptyList(),
    val likedProfiles: List<UserProfile> = emptyList(),
    val passedProfiles: List<UserProfile> = emptyList(),
    val lastAction: SwipeDirection? = null,
    val showMatchDialog: Boolean = false,
    val matchedProfile: UserProfile? = null
)

class DatingViewModel : ViewModel() {

    private val initialSampleProfiles: List<UserProfile> = listOf(
        UserProfile(
            id = "user_1",
            name = "Linh Đan",
            age = 23,
            bio = "Thích uống cà phê sữa đá, mê chụp ảnh film và nghe indie pop cuối tuần ☕️📸",
            imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=900&q=80",
            distance = "1.5 km",
            occupation = "UI/UX Designer",
            interests = listOf("Cà phê", "Nhiếp ảnh film", "Indie Music")
        ),
        UserProfile(
            id = "user_2",
            name = "Mai Anh",
            age = 25,
            bio = "Tìm bạn đồng hành đi trekking cuối tuần và ngắm hoàng hôn Hồ Tây 🌅🏃‍♀️",
            imageUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=900&q=80",
            distance = "3.2 km",
            occupation = "Kiến trúc sư cảnh quan",
            interests = listOf("Trekking", "Du lịch", "Yoga")
        ),
        UserProfile(
            id = "user_3",
            name = "Hoàng Yến",
            age = 22,
            bio = "Nhà có 2 em mèo béo, thích nấu món ngon và xem phim trinh thám ban đêm 🐱🍲",
            imageUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
            distance = "4.0 km",
            occupation = "Content Creator",
            interests = listOf("Nuôi mèo", "Nấu ăn", "Netflix")
        ),
        UserProfile(
            id = "user_4",
            name = "Thanh Trúc",
            age = 24,
            bio = "Lối sống lành mạnh, thích chạy bộ sáng sớm và thử các quán ăn ngon quanh phố cổ 🏃‍♀️🍜",
            imageUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=900&q=80",
            distance = "2.8 km",
            occupation = "Dược sĩ",
            interests = listOf("Chạy bộ", "Ẩm thực", "Đọc sách")
        ),
        UserProfile(
            id = "user_5",
            name = "Bảo Ngọc",
            age = 26,
            bio = "Nghiện podcast, thích chill acoustic ở quán nhỏ và những cuộc trò chuyện sâu sắc 🎧🪴",
            imageUrl = "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?auto=format&fit=crop&w=900&q=80",
            distance = "5.1 km",
            occupation = "Marketing Lead",
            interests = listOf("Podcast", "Acoustic", "Chăm cây")
        )
    )

    private val _uiState = MutableStateFlow(
        DatingUiState(profiles = initialSampleProfiles)
    )
    val uiState: StateFlow<DatingUiState> = _uiState.asStateFlow()

    /**
     * Quẹt trái: Bỏ qua (Pass) profile ở trên cùng.
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
     * Quẹt phải: Thích (Like) profile ở trên cùng.
     */
    fun onSwipeRight() {
        val currentList = _uiState.value.profiles
        if (currentList.isEmpty()) return

        val topProfile = currentList.first()
        val remaining = currentList.drop(1)

        // Tỷ lệ mô phỏng tương thích tương tác để tạo Match bất ngờ thú vị
        val isMatch = topProfile.id == "user_1" || topProfile.id == "user_3"

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
     * Siêu thích (Super Like)
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
     * Đóng dialog chúc mừng ghép đôi thành công (Match).
     */
    fun dismissMatchDialog() {
        _uiState.update { state ->
            state.copy(showMatchDialog = false, matchedProfile = null)
        }
    }

    /**
     * Hoàn tác quẹt thẻ trước đó.
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
     * Tải lại danh sách hồ sơ mẫu khi đã quẹt hết thẻ.
     */
    fun reloadProfiles() {
        _uiState.update {
            DatingUiState(
                profiles = initialSampleProfiles,
                likedProfiles = emptyList(),
                passedProfiles = emptyList(),
                lastAction = null,
                showMatchDialog = false,
                matchedProfile = null
            )
        }
    }
}
