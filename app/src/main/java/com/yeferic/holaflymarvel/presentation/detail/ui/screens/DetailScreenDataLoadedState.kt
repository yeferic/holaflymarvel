package com.yeferic.holaflymarvel.presentation.detail.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.theme.DarkHolo
import com.yeferic.holaflymarvel.domain.models.ComicDetail

enum class DetailScreenDataLoadedStateTags {
    LAZY_LIST,
    ITEM_IMAGE,
    ITEM_TITLE,
    ITEM_DESCRIPTION,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreenDataLoadedState(detail: ComicDetail) {
    LazyColumn(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag(DetailScreenDataLoadedStateTags.LAZY_LIST.name),
    ) {
        item {
            GetImageComponent(
                modifier =
                    Modifier
                        .fillParentMaxWidth()
                        .animateItemPlacement().testTag(
                            DetailScreenDataLoadedStateTags.ITEM_IMAGE.name,
                        ),
                imageUrl = detail.imageUrl,
            )

            Text(
                text = detail.title,
                modifier = Modifier.testTag(DetailScreenDataLoadedStateTags.ITEM_TITLE.name),
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
            )

            Text(
                text = detail.description,
                modifier =
                    Modifier.padding(
                        top = 8.dp,
                    ).testTag(DetailScreenDataLoadedStateTags.ITEM_DESCRIPTION.name),
                color = DarkHolo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable
fun GetImageComponent(
    modifier: Modifier,
    imageUrl: String,
) {
    Box(
        modifier =
            modifier
                .height(256.dp)
                .background(Color.White),
    ) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
            contentDescription =
                stringResource(
                    id = R.string.detail_screen_image_description,
                ),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )
    }
}
