package com.example.datingapp.domain.model

/**
 * Domain entity đại diện cho một hồ sơ người dùng trong hệ thống hẹn hò.
 * Tuân thủ Clean Architecture: Độc lập với framework UI và database.
 *
 * @property id Định danh duy nhất của người dùng.
 * @property name Tên hiển thị của người dùng.
 * @property age Tuổi của người dùng.
 * @property bio Đoạn giới thiệu bản thân ngắn gọn.
 * @property imageUrl Đường dẫn URL ảnh đại diện.
 * @property distance Khoảng cách tương đối đến người dùng hiện tại (ví dụ: "1.5 km").
 * @property occupation Nghề nghiệp hoặc trường học (tùy chọn).
 * @property interests Danh sách các sở thích / hashtag cá nhân (tùy chọn).
 */
data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val bio: String,
    val imageUrl: String,
    val distance: String,
    val occupation: String = "",
    val interests: List<String> = emptyList()
)
