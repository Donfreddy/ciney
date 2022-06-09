package com.freddydev.ciney.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.freddydev.ciney.R
import com.freddydev.ciney.domain.model.movie.Movie
import com.freddydev.ciney.ui.components.MovieCard
import com.freddydev.ciney.ui.home.components.TrendingCard
import com.freddydev.ciney.ui.profile.RoundImage
import com.freddydev.ciney.ui.theme.DavyGrey
import com.freddydev.ciney.util.DateTime.getCurrentDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
  var search by remember { mutableStateOf("") }
  val listState = rememberLazyListState()
  val trendingState = homeViewModel.trendingState.value

  LazyColumn(
    state = listState
  ) {

    item {
      TopAppBar(
        modifier = Modifier.padding(top = 10.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
          ) {
            Text(text = "Hello Freddy!", style = typography.h6, color = Color.White)
            Text(text = getCurrentDate(), style = typography.caption)
          }
          RoundImage(
            image = painterResource(id = R.drawable.freddy),
            modifier = Modifier
              .size(45.dp)
              .fillMaxWidth(3f)
          )
        }
      }
    }
    item { Spacer(modifier = Modifier.height(20.dp)) }

    item {
      TextInputField(
        label = stringResource(R.string.text_search),
        value = search,
        onValueChanged = {
          search = it
        })
    }
    item { Spacer(modifier = Modifier.height(10.dp)) }

    // What's Popular on TV
    item() {
      if (trendingState.isLoading) {
        CircularProgressIndicator(modifier = Modifier)
      }

      trendingState.trending?.let { trending ->
        Column() {
          RowTitle(title = "Trending Today")
          LazyRow() {
            items(trending.size) { index ->
              TrendingCard(trending = trending[index], selectPoster = {})
            }
          }
        }
      }

      if (trendingState.error.isNotBlank()) {
        Text(
          text = trendingState.error,
          textAlign = TextAlign.Center,
          color = Color.Red,
          style = MaterialTheme.typography.h6
        )
      }
    }
    item { Spacer(modifier = Modifier.height(10.dp)) }

    // Latest Trailers on TV
    item() {
      if (trendingState.isLoading) {
        CircularProgressIndicator(modifier = Modifier)
      }

      trendingState.trending?.let { trending ->
        Column() {
          RowTitle(title = "What's Popular on TV")
          LazyRow() {
            items(trending.size) { index ->
              TrendingCard(trending = trending[index], selectPoster = {})
            }
          }
        }
      }

      if (trendingState.error.isNotBlank()) {
        Text(
          text = trendingState.error,
          textAlign = TextAlign.Center,
          color = Color.Red,
          style = MaterialTheme.typography.h6
        )
      }
    }
    // Trending Today/This Week
  }
}

@Composable
fun RowTitle(
  title: String,
  onViewMore: () -> Unit = {}
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 10.dp, vertical = 14.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Bottom
  ) {
    Text(text = title, style = typography.h6)
    Text(
      text = "See All",
      color = MaterialTheme.colors.primary,
      modifier = Modifier
        .clickable { onViewMore() }
        .padding(vertical = 4.dp),
      fontWeight = FontWeight.Light,
      fontSize = 14.sp
    )
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInputField(label: String, value: String, onValueChanged: (String) -> Unit) {
  val keyboardController = LocalSoftwareKeyboardController.current

  OutlinedTextField(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 10.dp, end = 10.dp),
    shape = RoundedCornerShape(corner = CornerSize(12.dp)),
    value = "Search for a movie, tv show...",
    onValueChange = {
      onValueChanged(it)
    },
    placeholder = {
      Text(
        text = "Search for a movie, tv show...",
        color = DavyGrey,
        maxLines = 1,
        style = typography.body1,
        overflow = TextOverflow.Clip
      )
    },
    enabled = false,
    maxLines = 1,
    leadingIcon = {
      Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = null
      )
    },
    trailingIcon = {
      IconButton(onClick = { /*TODO*/ }) {
        Icon(
          painter = painterResource(id = R.drawable.ic_options),
          contentDescription = null
        )
      }
    },
    label = { Text(text = label) },
    textStyle = typography.body1,
    colors = textFieldColors(
      unfocusedIndicatorColor = Color.Transparent,
      focusedIndicatorColor = Color.Transparent
    ),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(
      onSearch = {},
      onDone = {
        keyboardController?.hide()
      }
    )
  )
}