package com.example.datingapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.datingapp.model.UserProfile
import com.example.ui.theme.LikeGreen
import com.example.ui.theme.PassRed
import com.example.ui.theme.RosePrimary
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
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

    // Tính toán góc nghiêng động theo độ dịch chuyển của ngón tay
    val rotation = (offsetX.value / 60f).coerceIn(-25f, 25f)
    val swipeThreshold = 300f

    Card(
        modifier = modifier
            .testTag("profile_card_${profile.id}")
            .offset {
                if (isTopCard) {
                    IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt())
                } else {
                    IntOffset(0, 0)
                }
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
                                            // Lướt sang phải (Like)
                                            offsetX.animateTo(
                                                targetValue = 1200f,
                                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                                            )
                                            onSwipeRight()
                                        }
                                        offsetX.value < -swipeThreshold -> {
                                            // Lướt sang trái (Pass)
                                            offsetX.animateTo(
                                                targetValue = -1200f,
                                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                                            )
                                            onSwipeLeft()
                                        }
                                        else -> {
                                            // Trở lại vị trí cân bằng nếu chưa đủ lực kéo
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isTopCard) 8.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Ảnh đại diện sử dụng Coil
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profile.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Ảnh của ${profile.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp)),
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = RosePrimary,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(RosePrimary, Color(0xFF1E272E)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = profile.name.take(1),
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )

            // Lớp Gradient tối dần ở phần dưới ảnh để thông tin nổi bật sắc nét
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color(0x66000000),
                                Color(0xDE000000),
                                Color(0xF8000000)
                            )
                        )
                    )
            )

            // Con dấu chỉ báo quẹt LIKE hoặc NOPE xuất hiện trực quan khi kéo thẻ
            if (isTopCard && offsetX.value > 60f) {
                val likeAlpha = ((offsetX.value - 60f) / 140f).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(24.dp)
                        .rotate(-15f)
                        .border(3.dp, LikeGreen.copy(alpha = likeAlpha), RoundedCornerShape(8.dp))
                        .background(LikeGreen.copy(alpha = 0.15f * likeAlpha), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "THÍCH",
                        color = LikeGreen.copy(alpha = likeAlpha),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                }
            } else if (isTopCard && offsetX.value < -60f) {
                val passAlpha = ((-offsetX.value - 60f) / 140f).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .rotate(15f)
                        .border(3.dp, PassRed.copy(alpha = passAlpha), RoundedCornerShape(8.dp))
                        .background(PassRed.copy(alpha = 0.15f * passAlpha), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "BỎ QUA",
                        color = PassRed.copy(alpha = passAlpha),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                }
            }

            // Phần thông tin hồ sơ đặt ở góc dưới của thẻ
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Tên + Tuổi + Huy hiệu xác thực
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "${profile.age}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 26.sp
                        ),
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Hồ sơ đã xác minh",
                        tint = Color(0xFF2ED573),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Nghề nghiệp & Khoảng cách
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (profile.occupation.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = profile.occupation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }

                    // Badge khoảng cách
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.45f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Khoảng cách",
                                tint = RosePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Cách bạn ${profile.distance}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                color = Color.White
                            )
                        }
                    }
                }

                // Tiểu sử (Bio)
                if (profile.bio.isNotEmpty()) {
                    Text(
                        text = profile.bio,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                        color = Color.White.copy(alpha = 0.92f),
                        maxLines = 3
                    )
                }

                // Các sở thích (Interests Tags)
                if (profile.interests.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        profile.interests.forEach { interest ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White.copy(alpha = 0.2f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = interest,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
