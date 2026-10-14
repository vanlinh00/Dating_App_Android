package com.example.datingapp.ui.screens.dating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LikeGreen
import com.example.ui.theme.PassRed

/**
 * Con dấu trực quan (Stamp) hiển thị trạng thái "THÍCH" hoặc "BỎ QUA" khi người dùng quẹt thẻ.
 * Tách biệt để giảm số tầng lồng nhau và số dòng code trong ProfileCard.
 */
@Composable
fun SwipeBadgeStamp(
    isLike: Boolean,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isLike) LikeGreen else PassRed
    val text = if (isLike) "THÍCH" else "BỎ QUA"
    val rotation = if (isLike) -15f else 15f

    Box(
        modifier = modifier
            .rotate(rotation)
            .border(3.dp, borderColor.copy(alpha = alpha), RoundedCornerShape(8.dp))
            .background(borderColor.copy(alpha = 0.15f * alpha), RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = borderColor.copy(alpha = alpha),
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
    }
}
