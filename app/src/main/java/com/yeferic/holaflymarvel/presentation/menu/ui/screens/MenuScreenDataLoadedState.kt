package com.yeferic.holaflymarvel.presentation.menu.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.domain.models.Character

data class MenuScreenDataLoadedParams(
    val characters: List<Character>,
    val goToComicsView: (Long) -> Unit,
)

@Composable
fun MenuScreenDataLoadedState(params: MenuScreenDataLoadedParams) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(params.characters) { item ->
                CardItem(name = item.name, imageUrl = item.imageUrl) {
                    params.goToComicsView(item.id)
                }
            }
        }
    }
}

@Composable
fun CardItem(
    name: String,
    imageUrl: String,
    onClickListener: () -> Unit,
) {
    ElevatedCard(
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
        modifier =
            Modifier.defaultMinSize(minHeight = 80.dp)
                .padding(4.dp)
                .clickable(enabled = true) { onClickListener.invoke() },
    ) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.menu_screen_image_description),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )

        Text(
            text = name,
            modifier =
                Modifier
                    .padding(16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}
