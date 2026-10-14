package com.example.datingapp.data.datasource

import com.example.datingapp.domain.model.UserProfile

/**
 * Data source giả lập (In-memory mock datasource) cung cấp danh sách hồ sơ ban đầu.
 * Tách biệt hoàn toàn việc tạo dữ liệu mẫu khỏi ViewModel theo nguyên lý SRP.
 */
class FakeProfileDataSource {

    fun getInitialProfiles(): List<UserProfile> = listOf(
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
}
