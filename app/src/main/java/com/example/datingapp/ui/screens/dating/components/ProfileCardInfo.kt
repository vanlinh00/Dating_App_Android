package com.example.datingapp.ui.screens.dating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datingapp.domain.model.UserProfile
import com.example.ui.theme.RosePrimary

/**
 * Hiển thị khối thông tin chi tiết của người dùng ở góc dưới thẻ (Tên, Tuổi, Nghề, Khoảng cách, Bio, Tags).
 * Tuân thủ SRP: Chỉ tập trung vào việc bố cục thông tin chữ và huy hiệu.
 */
@Composable
fun ProfileCardInfo(
    profile: UserProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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

        // Sở thích (Interests Tags)
        ProfileInterestTags(interests = profile.interests)
    }
}
