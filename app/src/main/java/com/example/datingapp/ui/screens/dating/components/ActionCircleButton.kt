package com.example.datingapp.ui.screens.dating.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Nút hành động hình tròn tái sử dụng (Atomic Component).
 * Tách biệt để tái sử dụng style, giảm trùng lặp layout lồng nhau trong ActionButtonBar.
 */
@Composable
fun ActionCircleButton(
    icon: ImageVector,
    contentDescription: String?,
    tint: Color,
    containerColor: Color,
    buttonSize: Dp,
    iconSize: Dp,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp = 6.dp,
    border: BorderStroke? = null
) {
    ElevatedButton(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor,
            contentColor = tint,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = tint.copy(alpha = 0.3f)
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = elevation,
            pressedElevation = 2.dp
        ),
        border = border,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .size(buttonSize)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled) tint else tint.copy(alpha = 0.3f),
            modifier = Modifier.size(iconSize)
        )
    }
}
