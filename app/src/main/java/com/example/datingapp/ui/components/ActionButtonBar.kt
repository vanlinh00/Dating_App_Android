package com.example.datingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.LikeGreen
import com.example.ui.theme.PassRed
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.StarYellow

/**
 * Thanh nút tương tác phía dưới gồm các nút chính Like / Pass và các tính năng bổ trợ.
 */
@Composable
fun ActionButtonBar(
    onPassClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSuperLikeClick: (() -> Unit)? = null,
    onUndoClick: (() -> Unit)? = null,
    isUndoEnabled: Boolean = false,
    isEnabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút Hoàn tác (Undo)
        if (onUndoClick != null) {
            OutlinedButton(
                onClick = onUndoClick,
                enabled = isEnabled && isUndoEnabled,
                shape = CircleShape,
                border = BorderStroke(
                    1.5.dp,
                    if (isUndoEnabled) Color(0xFFF39C12) else MaterialTheme.colorScheme.outlineVariant
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = Color(0xFFF39C12),
                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                modifier = Modifier
                    .size(48.dp)
                    .testTag("undo_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Hoàn tác lượt quẹt trước",
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Nút BỎ QUA (Dấu X màu đỏ)
        ElevatedButton(
            onClick = onPassClick,
            enabled = isEnabled,
            shape = CircleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PassRed
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
            modifier = Modifier
                .size(64.dp)
                .testTag("pass_button")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.pass_button_desc),
                tint = PassRed,
                modifier = Modifier.size(34.dp)
            )
        }

        // Nút SIÊU THÍCH (Ngôi sao vàng)
        if (onSuperLikeClick != null) {
            ElevatedButton(
                onClick = onSuperLikeClick,
                enabled = isEnabled,
                shape = CircleShape,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = StarYellow
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 2.dp
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                modifier = Modifier
                    .size(50.dp)
                    .testTag("superlike_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = R.string.superlike_button_desc),
                    tint = StarYellow,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        // Nút THÍCH (Trái tim màu hồng/xanh)
        ElevatedButton(
            onClick = onLikeClick,
            enabled = isEnabled,
            shape = CircleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = RosePrimary
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
            modifier = Modifier
                .size(64.dp)
                .testTag("like_button")
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.like_button_desc),
                tint = RosePrimary,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}
