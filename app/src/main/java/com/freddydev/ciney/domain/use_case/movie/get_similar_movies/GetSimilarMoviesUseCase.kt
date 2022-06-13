package com.freddydev.ciney.domain.use_case.movie.get_similar_movies

import com.freddydev.ciney.domain.model.movie.Movie
import com.freddydev.ciney.domain.repository.MovieRepository
import com.freddydev.ciney.domain.use_case.UseCase
import com.freddydev.ciney.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
  private val movieRepos: MovieRepository,
) : UseCase<List<Movie>, GetSimilarMoviesUseCase.Params>() {

  fun execute(params: Params): Flow<Resource<List<Movie>>> {
    return movieRepos.getSimilarMovies(movieId = params.movieId, page = params.page)
  }

  data class Params(val movieId: Int, val page: Int)
}