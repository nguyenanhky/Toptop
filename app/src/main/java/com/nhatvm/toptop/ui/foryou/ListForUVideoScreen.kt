package com.nhatvm.toptop.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.video.VideoDetailScreen
import com.nhatvm.toptop.ui.video.VideoDetailViewModel

@UnstableApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListForUVideoScreen(
    modifier: Modifier = Modifier,
) {
    VerticalPager(pageCount = 10) { videoId ->
        val videoDetailViewModel: VideoDetailViewModel = hiltViewModel(key = videoId.toString())
        VideoDetailScreen(videoId = videoId, videoDetailViewModel = videoDetailViewModel)
    }
}