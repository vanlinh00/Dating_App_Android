package com.example.datingapp.ui.screens.dating.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.example.datingapp.domain.model.UserProfile

/**
 * Quản lý ngăn xếp thẻ (Card Stack) hiển thị thẻ hiện tại và thẻ dự phòng phía dưới tạo hiệu ứng chiều sâu 3D.
 * Tách biệt để giữ DatingScreen gọn gàng, dưới 150 dòng và tăng tính module hóa.
 */
@Composable
fun ProfileCardStack(
    profiles: List<UserProfile>,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (profiles.isEmpty()) return

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Thẻ dự phòng phía sau (nếu còn)
        if (profiles.size > 1) {
            ProfileCard(
                profile = profiles[1],
                isTopCard = false,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.95f)
            )
        }

        // Thẻ đang tương tác ở trên cùng
        val topProfile = profiles.first()
        key(topProfile.id) {
            ProfileCard(
                profile = topProfile,
                isTopCard = true,
                onSwipeLeft = onSwipeLeft,
                onSwipeRight = onSwipeRight,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
