package com.nhatvm.toptop.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.data.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    val videoPlayer: ExoPlayer,
    val videoRepository: VideoRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Default)
    val uiState: StateFlow<VideoDetailUiState>
        get() = _uiState

    init {
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
        videoPlayer.prepare()
    }
    fun handleAction(action: VideoDetailAction){
        when(action){
            is VideoDetailAction.LoadData->{
                val videoId = action.id
                loadVideo(videoId = videoId)
            }
            is VideoDetailAction.ToggleVideo->{

            }
        }
    }

    private fun loadVideo(videoId:Int){
        _uiState.value = VideoDetailUiState.Loading
        viewModelScope.launch {
            delay(100L)
            val video = videoRepository.getVideo()
            // play
            playVideo(videoResourceId = video)
            _uiState.value = VideoDetailUiState.Success
        }
    }

    private fun playVideo(videoResourceId:Int){
        val uri = RawResourceDataSource.buildRawResourceUri(videoResourceId)
        val mediaItem  = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.play()
    }

}
//MVVM
// MVI

sealed interface VideoDetailUiState {
    object Default : VideoDetailUiState
    object Loading : VideoDetailUiState
    object Success : VideoDetailUiState
    data class Error(val msg: String) : VideoDetailUiState
}

sealed class VideoDetailAction{
    data class LoadData(val id:Int):VideoDetailAction()
    object ToggleVideo:VideoDetailAction()

}


