package com.nhatvm.toptop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.MainScreen
import com.nhatvm.toptop.ui.following.FollowingScreen
import com.nhatvm.toptop.ui.foryou.ListForUVideoScreen
import com.nhatvm.toptop.ui.theme.ToptopTheme
import dagger.hilt.android.AndroidEntryPoint

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToptopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemoPager() {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Magenta,
    )
    VerticalPager(pageCount = 10) { pageId ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.random()),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Page $pageId")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToptopTheme {
        DemoPager()
    }
}