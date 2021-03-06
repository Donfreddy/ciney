package com.freddydev.ciney.data.repository.movie

import android.content.Context
import androidx.annotation.WorkerThread
import com.freddydev.ciney.data.dto.credit.toCreditsResult
import com.freddydev.ciney.data.dto.image.toImagesResult
import com.freddydev.ciney.data.dto.movie.toMovieDetail
import com.freddydev.ciney.data.dto.movie.toMovies
import com.freddydev.ciney.data.dto.review.toReviewsResult
import com.freddydev.ciney.data.dto.video.toVideosResult
import com.freddydev.ciney.data.repository.movie.data_source.MovieLocalDatasource
import com.freddydev.ciney.data.repository.movie.data_source.MovieRemoteDatasource
import com.freddydev.ciney.domain.model.credit.CreditsResult
import com.freddydev.ciney.domain.model.image.ImagesResult
import com.freddydev.ciney.domain.model.movie.Movie
import com.freddydev.ciney.domain.model.movie.MovieDetail
import com.freddydev.ciney.domain.model.review.Review
import com.freddydev.ciney.domain.model.video.Video
import com.freddydev.ciney.domain.repository.MovieRepository
import com.freddydev.ciney.ui.common.Constants.HTTP_EXCEPT_MSG
import com.freddydev.ciney.ui.common.Constants.IO_EXCEPT_MSG
import com.freddydev.ciney.util.Resource
import com.freddydev.ciney.util.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * This class is a implementation of [MovieRepository]
 */
@WorkerThread
class MovieRepositoryImpl constructor(
  private val context: Context,
  private val movieRemoteDatasource: MovieRemoteDatasource,
  private val movieLocalDataSource: MovieLocalDatasource,
) : MovieRepository {

  override fun getMovies(category: String, page: Int): Flow<Resource<List<Movie>>> = flow {
    val isNetworkAvailable = Utils.isConnected(context)
    println("### Is Network Available: $isNetworkAvailable")

    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovies(category, page).body()
      if (responseBody != null) {
        val movies: List<Movie> = responseBody.toMovies().movies

        // Todo: Save movies in local database

        emit(Resource.Success(movies))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      /**
       * Todo: Get data from [movieLocalDataSource] if no connexion.
       */
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovieDetail(movieId = movieId).body()
      if (responseBody != null) {
        val movieDetail: MovieDetail = responseBody.toMovieDetail()
        emit(Resource.Success(movieDetail))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getSimilarMovies(movieId: Int, page: Int): Flow<Resource<List<Movie>>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getSimilarMovies(movieId, page).body()
      if (responseBody != null) {
        val movies: List<Movie> = responseBody.toMovies().movies
        emit(Resource.Success(movies))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getRecommendedMovies(movieId: Int, page: Int): Flow<Resource<List<Movie>>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getRecommendedMovies(movieId, page).body()
      if (responseBody != null) {
        val movies: List<Movie> = responseBody.toMovies().movies
        emit(Resource.Success(movies))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovieVideos(movieId).body()
      println("### responseBody")
      println(responseBody)
      println("### responseBody")
      if (responseBody != null) {
        val videos: List<Video> = responseBody.toVideosResult().videos
        emit(Resource.Success(videos))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovieReviews(movieId).body()
      if (responseBody != null) {
        val reviews: List<Review> = responseBody.toReviewsResult().reviews
        emit(Resource.Success(reviews))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getMovieCredits(movieId: Int): Flow<Resource<CreditsResult>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovieCredits(movieId).body()
      if (responseBody != null) {
        val credits: CreditsResult = responseBody.toCreditsResult()
        emit(Resource.Success(credits))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }

  override fun getMovieImages(movieId: Int): Flow<Resource<ImagesResult>> = flow {
    try {
      emit(Resource.Loading())
      val responseBody = movieRemoteDatasource.getMovieImages(movieId).body()
      if (responseBody != null) {
        val images: ImagesResult = responseBody.toImagesResult()
        emit(Resource.Success(images))
      }
    } catch (e: HttpException) {
      emit(Resource.Error(message = HTTP_EXCEPT_MSG))
    } catch (e: IOException) {
      emit(Resource.Error(message = IO_EXCEPT_MSG))
    }
  }
}