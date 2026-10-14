package com.example.datingapp.data.repository

import com.example.datingapp.data.datasource.FakeProfileDataSource
import com.example.datingapp.domain.model.UserProfile

/**
 * Hiện thực hóa DatingRepository sử dụng nguồn dữ liệu FakeProfileDataSource.
 */
class DatingRepositoryImpl(
    private val dataSource: FakeProfileDataSource = FakeProfileDataSource()
) : DatingRepository {

    override fun getProfiles(): List<UserProfile> {
        return dataSource.getInitialProfiles()
    }

    override fun isMatch(profile: UserProfile): Boolean {
        // Mô phỏng tỷ lệ tương thích dựa trên ID hồ sơ
        return profile.id == "user_1" || profile.id == "user_3"
    }
}
