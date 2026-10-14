package com.example.datingapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.R
import com.example.datingapp.ui.components.ActionButtonBar
import com.example.datingapp.ui.components.ProfileCard
import com.example.datingapp.viewmodel.DatingViewModel
import com.example.ui.theme.RosePrimary

@Composable
fun DatingScreen(
    viewModel: DatingViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val profiles = uiState.profiles

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DatingTopBar(remainingCount = profiles.size)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .widthIn(max = 600.dp),
            contentAlignment = Alignment.Center
        ) {
            if (profiles.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Ngăn xếp Thẻ Hồ Sơ (Profile Card Stack)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Thẻ kế tiếp nằm bên dưới (nếu còn)
                        if (profiles.size > 1) {
                            val nextProfile = profiles[1]
                            ProfileCard(
                                profile = nextProfile,
                                isTopCard = false,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(0.95f)
                            )
                        }

                        // Thẻ chính hiện tại ở trên cùng
                        val topProfile = profiles.first()
                        androidx.compose.runtime.key(topProfile.id) {
                            ProfileCard(
                                profile = topProfile,
                                isTopCard = true,
                                onSwipeLeft = { viewModel.onSwipeLeft() },
                                onSwipeRight = { viewModel.onSwipeRight() },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // Thanh nút bấm điều khiển Like / Pass
                    ActionButtonBar(
                        onPassClick = { viewModel.onSwipeLeft() },
                        onLikeClick = { viewModel.onSwipeRight() },
                        onSuperLikeClick = { viewModel.onSuperLike() },
                        onUndoClick = { viewModel.undoLastSwipe() },
                        isUndoEnabled = uiState.passedProfiles.isNotEmpty() || uiState.likedProfiles.isNotEmpty(),
                        isEnabled = true
                    )
                }
            } else {
                // Trạng thái trống khi đã duyệt hết toàn bộ hồ sơ
                EmptyProfilesState(
                    onReloadClick = { viewModel.reloadProfiles() },
                    likedCount = uiState.likedProfiles.size,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }

    // Dialog thông báo Match khi hai người tương thích
    if (uiState.showMatchDialog && uiState.matchedProfile != null) {
        val matched = uiState.matchedProfile!!
        AlertDialog(
            onDismissRequest = { viewModel.dismissMatchDialog() },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissMatchDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("match_continue_button")
                )
            {
                Text("Tiếp tục quẹt thẻ", fontWeight = FontWeight.Bold)
            }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.dismissMatchDialog() },
                    modifier = Modifier.testTag("match_close_button")
                ) {
                    Text("Đóng")
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = RosePrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Tương Hợp Tuyệt Vời!",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = RosePrimary
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = matched.imageUrl,
                        contentDescription = "Ảnh của ${matched.name}",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Bạn và ${matched.name} (${matched.age} tuổi) đã thích nhau! Hãy bắt đầu làm quen nhé.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            shape = RoundedCornerShape(28.dp)
        )
    }
}

/**
 * Header thanh trên cùng hiển thị nhận diện thương hiệu và số lượng thẻ còn lại.
 */
@Composable
fun DatingTopBar(
    remainingCount: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = RosePrimary.copy(alpha = 0.15f),
                    shape = CircleShape,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Cupid Fire",
                            tint = RosePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.app_title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PeopleAlt,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$remainingCount",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Màn hình thông báo khi không còn hồ sơ nào trong bán kính tìm kiếm.
 */
@Composable
fun EmptyProfilesState(
    onReloadClick: () -> Unit,
    likedCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = RosePrimary.copy(alpha = 0.1f),
            modifier = Modifier.size(120.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = RosePrimary,
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.empty_title),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (likedCount > 0) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "Bạn đã thích $likedCount người trong phiên này",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onReloadClick,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 28.dp,
                vertical = 14.dp
            ),
            modifier = Modifier.testTag("reload_button")
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.reload_button),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

