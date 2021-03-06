package com.freddydev.ciney.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.freddydev.ciney.BuildConfig.BASE_POSTER_URL
import com.freddydev.ciney.domain.model.trending.Trending

@Composable
fun TrendingCard(
  trending: Trending,
  modifier: Modifier = Modifier,
  selectPoster: (Int, String) -> Unit,
) {
  Box(
    modifier = modifier
      .padding(horizontal = 4.dp)
  ) {

    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(data = BASE_POSTER_URL + trending.poster_path).build(),
      contentDescription = "Poster",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .aspectRatio(2 / 3f, matchHeightConstraintsFirst = true)
        .clip(
          RoundedCornerShape(corner = CornerSize((10.dp)))
        )
        .clickable { selectPoster(trending.id, trending.media_type) }
    )


//    CoilImage(
//      imageModel = BASE_POSTER_URL + trending.poster_path,
//      contentScale = ContentScale.Crop,
//      // placeHolder = ImageBitmap.imageResource(R.drawable.ic_info),
//     //  error = ImageBitmap.imageResource(R.drawable.ic_alert_triangle),
//      modifier = Modifier
//        .aspectRatio(2 / 3f, matchHeightConstraintsFirst = true)
//        .clip(
//          RoundedCornerShape(corner = CornerSize((10.dp)))
//        )
//        .clickable { selectPoster(trending.id) }
//    )


//    Text(
//      text = trending.name ?: trending.original_name ?: trending.title ?: trending.original_title
//      ?: "",
//      maxLines = 1,
//      overflow = TextOverflow.Clip,
//      fontSize = 12.sp,
//      modifier = Modifier
//        .padding(top = 8.dp)
//        .wrapContentWidth()
//    )
//    Text(
//      text = (trending.release_date ?: trending.first_air_date)?.let { getShortDate(it) } ?: "",
//      //  color = DavyGrey,
//      style = MaterialTheme.typography.caption
//    )

//    IconButton(onClick = { /*TODO*/ }) {
//      Icon(
//        painter = painterResource(id = R.drawable.ic_more_horizontal),
//        contentDescription = null
//      )
//    }
  }
}