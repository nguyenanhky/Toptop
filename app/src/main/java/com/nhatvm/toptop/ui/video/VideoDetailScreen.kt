package com.nhatvm.toptop.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.designsystem.TopTopVideoPlayer
import com.nhatvm.toptop.ui.video.composables.SideBarView
import com.nhatvm.toptop.ui.video.composables.VideoInfoArea


@UnstableApi
@Composable
fun VideoDetailScreen(
    videoId: Int,
    videoDetailViewModel: VideoDetailViewModel = hiltViewModel(),
) {
    val uiState = videoDetailViewModel.uiState.collectAsState()
    if (uiState.value == VideoDetailUiState.Default) {
        // loading
        videoDetailViewModel.handleAction(VideoDetailAction.LoadData(videoId))
    }
    VideoDetailScreen(
        uiState = uiState.value,
        player = videoDetailViewModel.videoPlayer
    ) { videoDetailAction ->
        videoDetailViewModel.handleAction(videoDetailAction)
    }
}

@UnstableApi
@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUiState,
    player: Player,
    handleAction: (VideoDetailAction) -> Unit,
) {
    when (uiState) {
        is VideoDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading....")
            }
        }

        is VideoDetailUiState.Success -> {
            VideoDetailScreen(player = player, handleAction = handleAction)
        }

        else -> {

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@UnstableApi
@Composable
fun VideoDetailScreen(
    player: Player,
    handleAction: (VideoDetailAction) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = {
                    handleAction(VideoDetailAction.ToggleVideo)
                }
            )
    ) {
        val (videoPlayerView, sideBar,videoInfo) = createRefs()
        TopTopVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayerView) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.matchParent
        })
        SideBarView(
            onAvatarClick = { /*TODO*/ },
            onLikeClick = { /*TODO*/ },
            onChatClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ },
            onShareClick = {},
            modifier = Modifier.constrainAs(sideBar) {
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
        VideoInfoArea(
            accountName = "KyNV1",
            videoName = "Clone TikTok",
            hashTags = listOf("Jetpack compose", "android", "tiktok"),
            songName = "Making my way",
            modifier = Modifier.constrainAs(videoInfo){
                start.linkTo(parent.start)
                bottom.linkTo(sideBar.bottom)
                end.linkTo(sideBar.start)
                width = Dimension.fillToConstraints
            }
        )
    }
}


