package com.example.datingapp.ui.screens.dating.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.datingapp.domain.model.UserProfile
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Thẻ hiển thị hồ sơ hẹn hò tích hợp thao tác kéo vuốt mượt mà.
 * Ủy quyền hiển thị ảnh cho ProfileImageBackground, thông tin cho ProfileCardInfo và con dấu cho SwipeBadgeStamp.
 */
@Composable
fun ProfileCard(
    profile: UserProfile,
    modifier: Modifier = Modifier,
    isTopCard: Boolean = true,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val rotation = (offsetX.value / 60f).coerceIn(-25f, 25f)
    val swipeThreshold = 300f

    Card(
        modifier = modifier
            .testTag("profile_card_${profile.id}")
            .offset {
                if (isTopCard) IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt())
                else IntOffset(0, 0)
            }
            .rotate(if (isTopCard) rotation else 0f)
            .then(
                if (isTopCard) {
                    Modifier.pointerInput(profile.id) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    when {
                                        offsetX.value > swipeThreshold -> {
                                            offsetX.animateTo(1200f, spring(stiffness = Spring.StiffnessMediumLow))
                                            onSwipeRight()
                                        }
                                        offsetX.value < -swipeThreshold -> {
                                            offsetX.animateTo(-1200f, spring(stiffness = Spring.StiffnessMediumLow))
                                            onSwipeLeft()
                                        }
                                        else -> {
                                            launch { offsetX.animateTo(0f, spring(dampingRatio = 0.7f)) }
                                            launch { offsetY.animateTo(0f, spring(dampingRatio = 0.7f)) }
                                        }
                                    }
                                }
                            },
                            onDragCancel = {
                                coroutineScope.launch {
                                    offsetX.animateTo(0f)
                                    offsetY.animateTo(0f)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                coroutineScope.launch {
                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                    offsetY.snapTo(offsetY.value + dragAmount.y * 0.4f)
                                }
                            }
                        )
                    }
                } else Modifier
            ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isTopCard) 8.dp else 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Ảnh nền và gradient tối màu
            ProfileImageBackground(
                imageUrl = profile.imageUrl,
                userName = profile.name
            )

            // Con dấu THÍCH hoặc BỎ QUA khi kéo
            if (isTopCard && offsetX.value > 60f) {
                val alpha = ((offsetX.value - 60f) / 140f).coerceIn(0f, 1f)
                SwipeBadgeStamp(
                    isLike = true,
                    alpha = alpha,
                    modifier = Modifier.align(Alignment.TopStart).padding(24.dp)
                )
            } else if (isTopCard && offsetX.value < -60f) {
                val alpha = ((-offsetX.value - 60f) / 140f).coerceIn(0f, 1f)
                SwipeBadgeStamp(
                    isLike = false,
                    alpha = alpha,
                    modifier = Modifier.align(Alignment.TopEnd).padding(24.dp)
                )
            }

            // Khối thông tin chi tiết người dùng
            ProfileCardInfo(
                profile = profile,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}
