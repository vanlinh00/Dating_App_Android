package com.example.datingapp.data.repository

import com.example.datingapp.domain.model.UserProfile

/**
 * Trừu tượng hóa tầng dữ liệu (Data Layer Abstraction) cho ứng dụng hẹn hò.
 * Giúp dễ dàng thay thế bằng Firebase Firestore, Retrofit Remote API hoặc Room Database mà không ảnh hưởng tầng UI.
 */
interface DatingRepository {
    fun getProfiles(): List<UserProfile>
    fun isMatch(profile: UserProfile): Boolean
}
