package com.example.datingapp.ui.screens.dating.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.PassRed
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.StarYellow

/**
 * Thanh nút tương tác phía dưới gồm các nút chính Like / Pass và nút phụ Undo / SuperLike.
 * Sử dụng ActionCircleButton để giữ code gọn gàng, phẳng và chuẩn SRP.
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
    val surfaceColor = MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút Hoàn tác (Undo)
        if (onUndoClick != null) {
            ActionCircleButton(
                icon = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.reload_button_desc),
                tint = Color(0xFFF39C12),
                containerColor = surfaceColor,
                buttonSize = 48.dp,
                iconSize = 22.dp,
                onClick = onUndoClick,
                testTag = "undo_button",
                enabled = isEnabled && isUndoEnabled,
                elevation = 2.dp,
                border = BorderStroke(
                    1.5.dp,
                    if (isUndoEnabled) Color(0xFFF39C12) else MaterialTheme.colorScheme.outlineVariant
                )
            )
        }

        // Nút BỎ QUA (Dấu X màu đỏ)
        ActionCircleButton(
            icon = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.pass_button_desc),
            tint = PassRed,
            containerColor = surfaceColor,
            buttonSize = 64.dp,
            iconSize = 34.dp,
            onClick = onPassClick,
            testTag = "pass_button",
            enabled = isEnabled
        )

        // Nút SIÊU THÍCH (Ngôi sao vàng)
        if (onSuperLikeClick != null) {
            ActionCircleButton(
                icon = Icons.Default.Star,
                contentDescription = stringResource(id = R.string.superlike_button_desc),
                tint = StarYellow,
                containerColor = surfaceColor,
                buttonSize = 50.dp,
                iconSize = 26.dp,
                onClick = onSuperLikeClick,
                testTag = "superlike_button",
                enabled = isEnabled,
                elevation = 4.dp
            )
        }

        // Nút THÍCH (Trái tim màu hồng)
        ActionCircleButton(
            icon = Icons.Default.Favorite,
            contentDescription = stringResource(id = R.string.like_button_desc),
            tint = RosePrimary,
            containerColor = surfaceColor,
            buttonSize = 64.dp,
            iconSize = 34.dp,
            onClick = onLikeClick,
            testTag = "like_button",
            enabled = isEnabled
        )
    }
}
