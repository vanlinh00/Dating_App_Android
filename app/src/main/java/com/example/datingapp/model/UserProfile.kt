package com.example.datingapp.model

/**
 * Data model representing a dating profile.
 *
 * @property id Unique identifier for the user.
 * @property name Display name of the user.
 * @property age Age of the user.
 * @property bio Short self-introduction / bio description.
 * @property imageUrl URL to the profile avatar image.
 * @property distance Distance string relative to the current user (e.g. "2 km").
 * @property occupation Optional profession or school.
 * @property interests Optional list of passions / hobbies.
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
