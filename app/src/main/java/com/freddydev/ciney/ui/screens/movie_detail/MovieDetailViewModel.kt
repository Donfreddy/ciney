package com.freddydev.ciney.ui.screens.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freddydev.ciney.domain.use_case.movie.get_movie.GetMovieUseCase
import com.freddydev.ciney.domain.use_case.movie.get_movie_credits.GetMovieCreditsUseCase
import com.freddydev.ciney.domain.use_case.movie.get_movie_images.GetMovieImagesUseCase
import com.freddydev.ciney.domain.use_case.movie.get_movie_reviews.GetMovieReviewsUseCase
import com.freddydev.ciney.domain.use_case.movie.get_movie_videos.GetMovieVideosUseCase
import com.freddydev.ciney.domain.use_case.movie.get_recommended_movies.GetRecommendedMoviesUseCase
import com.freddydev.ciney.domain.use_case.movie.get_similar_movies.GetSimilarMoviesUseCase
import com.freddydev.ciney.ui.common.Constants
import com.freddydev.ciney.ui.screens.media_videos.VideoState
import com.freddydev.ciney.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val getMovie: GetMovieUseCase,
  private val getSimilarMovies: GetSimilarMoviesUseCase,
  private val getRecommendedMovies: GetRecommendedMoviesUseCase,
  private val getMovieVideos: GetMovieVideosUseCase,
  private val getMovieReviews: GetMovieReviewsUseCase,
  private val getMovieCredits: GetMovieCreditsUseCase,
  private val getMovieImages: GetMovieImagesUseCase,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _movieDetailState = mutableStateOf(MovieDetailState())
  val movieDetailState: State<MovieDetailState> = _movieDetailState

  // Trailers state
  private val _trailersState = mutableStateOf(VideoState())
  val trailersState: State<VideoState> = _trailersState


  init {
    savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
      getMovieDetail(movieId.toInt())
      getMovieVideos(movieId.toInt())
    }
  }

  /**
   * Get movie detail.
   */
  private fun getMovieDetail(movieId: Int) {
    getMovie.execute(params = GetMovieUseCase.Params(movieId = movieId))
      .onEach { result ->
        when (result) {
          is Resource.Loading -> {
            _movieDetailState.value = MovieDetailState(isLoading = true)
          }
          is Resource.Success -> {
            _movieDetailState.value = MovieDetailState(movie = result.data)
          }
          is Resource.Error -> {
            _movieDetailState.value =
              MovieDetailState(error = "${result.message} ?: ${Constants.HTTP_EXCEPT_MSG}")
          }
        }
      }.launchIn(viewModelScope)
  }

  /**
   * Get similar movies.
   */
  private fun getSimilarMovies(movieId: Int) {
//    getSimilarMovies.execute(params = GetSimilarMoviesUseCase.Params(movieId = movieId))
//      .onEach { result ->
//        when (result) {
//          is Resource.Loading -> {
//            _movieDetailState.value = MovieDetailState(isLoading = true)
//          }
//          is Resource.Success -> {
//            _movieDetailState.value = MovieDetailState(similarMovies = result.data)
//          }
//          is Resource.Error -> {
//            _movieDetailState.value =
//              MovieDetailState(error = "${result.message} ?: ${Constants.HTTP_EXCEPT_MSG}")
//          }
//        }
//      }.launchIn(viewModelScope)
  }

  /**
   * Get recommended movies.
   */
  private fun getRecommendedMovies(movieId: Int) {
//    getRecommendedMovies.execute(params = GetRecommendedMoviesUseCase.Params(movieId = movieId))
//      .onEach { result ->
//        when (result) {
//          is Resource.Loading -> {
//            _movieDetailState.value = MovieDetailState(isLoading = true)
//          }
//          is Resource.Success -> {
//            _movieDetailState.value = MovieDetailState(recommendedMovies = result.data)
//          }
//          is Resource.Error -> {
//            _movieDetailState.value =
//              MovieDetailState(error = "${result.message} ?: ${Constants.HTTP_EXCEPT_MSG}")
//          }
//        }
//      }.launchIn(viewModelScope)
  }

  /**
   * Get movie videos.
   */
  private fun getMovieVideos(movieId: Int) {
    getMovieVideos.execute(params = GetMovieVideosUseCase.Params(movieId = movieId))
      .onEach { result ->
        when (result) {
          is Resource.Loading -> {
            _trailersState.value = VideoState(isLoading = true)
          }
          is Resource.Success -> {
            println("### Movie Videos Success state")
            println(result.data)
            _trailersState.value = VideoState(videos = result.data ?: emptyList())
          }
          is Resource.Error -> {
            _trailersState.value =
              VideoState(error = "${result.message} ?: ${Constants.HTTP_EXCEPT_MSG}")
          }
        }
      }.launchIn(viewModelScope)
  }
}