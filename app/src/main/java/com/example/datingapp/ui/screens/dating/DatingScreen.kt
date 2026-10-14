package com.example.datingapp.ui.screens.dating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datingapp.ui.screens.dating.components.ActionButtonBar
import com.example.datingapp.ui.screens.dating.components.DatingTopBar
import com.example.datingapp.ui.screens.dating.components.EmptyProfilesView
import com.example.datingapp.ui.screens.dating.components.MatchDialog
import com.example.datingapp.ui.screens.dating.components.ProfileCardStack

/**
 * Stateful Composable: Điểm kết nối với ViewModel và vòng đời ứng dụng.
 * Thu thập StateFlow và truyền dữ liệu/sự kiện xuống Stateless Composable (DatingContent).
 */
@Composable
fun DatingScreen(
    viewModel: DatingViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    DatingContent(
        uiState = uiState,
        onSwipeLeft = viewModel::onSwipeLeft,
        onSwipeRight = viewModel::onSwipeRight,
        onSuperLike = viewModel::onSuperLike,
        onUndoSwipe = viewModel::undoLastSwipe,
        onReload = viewModel::reloadProfiles,
        onDismissMatch = viewModel::dismissMatchDialog,
        modifier = modifier
    )
}

/**
 * Stateless Composable: Tuân thủ quy tắc Hoisting State.
 * Không phụ thuộc vào ViewModel, dễ dàng viết Preview hoặc UI Automated Test.
 */
@Composable
fun DatingContent(
    uiState: DatingUiState,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onSuperLike: () -> Unit,
    onUndoSwipe: () -> Unit,
    onReload: () -> Unit,
    onDismissMatch: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    // Ngăn xếp thẻ hồ sơ
                    ProfileCardStack(
                        profiles = profiles,
                        onSwipeLeft = onSwipeLeft,
                        onSwipeRight = onSwipeRight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    )

                    // Thanh nút bấm điều khiển
                    ActionButtonBar(
                        onPassClick = onSwipeLeft,
                        onLikeClick = onSwipeRight,
                        onSuperLikeClick = onSuperLike,
                        onUndoClick = onUndoSwipe,
                        isUndoEnabled = uiState.passedProfiles.isNotEmpty() || uiState.likedProfiles.isNotEmpty(),
                        isEnabled = true
                    )
                }
            } else {
                EmptyProfilesView(
                    onReloadClick = onReload,
                    likedCount = uiState.likedProfiles.size,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }

    // Hộp thoại chúc mừng tương hợp
    if (uiState.showMatchDialog && uiState.matchedProfile != null) {
        MatchDialog(
            matchedProfile = uiState.matchedProfile,
            onDismiss = onDismissMatch
        )
    }
}
