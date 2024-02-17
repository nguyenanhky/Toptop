package com.nhatvm.toptop.ui.following

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.R
import com.nhatvm.toptop.designsystem.TopTopVideoPlayer
import com.nhatvm.toptop.ui.theme.ToptopTheme

@UnstableApi
@Composable
fun FollowingScreen(
    
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        CreatorCard(name = "Donal Trump", nickName = "@trumg", onFollow = { /*TODO*/ }) {

        }
    }
}

@UnstableApi
@Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    name: String,
    nickName: String,
    onFollow: () -> Unit,
    onClose: () -> Unit,
) {
    val videoPlayer = ExoPlayer.Builder(LocalContext.current).build()
    videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    videoPlayer.playWhenReady = true
    videoPlayer.prepare()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val (videoInfo, btnClose, imgAvatar, tvName, tvNickName, btnFollow) = createRefs()
        TopTopVideoPlayer(player = videoPlayer, modifier = Modifier.constrainAs(videoInfo) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        IconButton(onClick = onClose, modifier = Modifier
            .constrainAs(btnClose) {
                top.linkTo(parent.top, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
            }
            .size(16.dp)
        ) {
            Icon(
                imageVector = Icons.Sharp.Close,
                contentDescription = "close icon",
                tint = Color.White
            )
        }

        Button(onClick = onFollow, modifier = Modifier
            .constrainAs(btnFollow) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            }
            .padding(horizontal = 56.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE94359),
                contentColor = Color.White
            )
        ) {
            Text(text = "Follow", style = MaterialTheme.typography.body1)
        }
        Text(
            text = nickName,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray),
            modifier = Modifier.constrainAs(tvNickName) {
                bottom.linkTo(btnFollow.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        Text(
            text = name,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier.constrainAs(tvName) {
                bottom.linkTo(tvNickName.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        AvatarFollowing(modifier = Modifier.constrainAs(imgAvatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(tvName.top, margin = 8.dp)
        })

        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.khesanh)
        val mediaItem = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)
        SideEffect {
            videoPlayer.play()
        }
    }

}

@Composable
fun AvatarFollowing(
    modifier: Modifier = Modifier,
) {
    val sizeAvatar = LocalConfiguration.current.screenWidthDp * 0.2
    Image(
        painter = painterResource(id = R.drawable.ic_dog),
        contentDescription = "avatar",
        modifier = modifier
            .size(sizeAvatar.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(
                color = Color.White, width = 2.dp, shape = CircleShape
            )
            .clip(CircleShape)
    )
}


@Preview(showBackground = false)
@Composable
fun AvatarFollowingPreview() {
    ToptopTheme {
        AvatarFollowing()
    }
}