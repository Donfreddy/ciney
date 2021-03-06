package com.freddydev.ciney.di

import com.freddydev.ciney.data.repository.movie.data_source.MovieRemoteDatasource
import com.freddydev.ciney.data.api.services.MovieService
import com.freddydev.ciney.data.api.services.TrendingService
import com.freddydev.ciney.data.repository.movie.data_source_impl.MovieRemoteDatasourceImpl
import com.freddydev.ciney.data.repository.trenting.data_source.TrendingRemoteDatasource
import com.freddydev.ciney.data.repository.trenting.data_source_impl.TrendingRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

  @Singleton
  @Provides
  fun provideRemoteTrendingDataSource(trendingService: TrendingService): TrendingRemoteDatasource {
    return TrendingRemoteDatasourceImpl(trendingService = trendingService)
  }

  @Singleton
  @Provides
  fun provideRemoteMovieDataSource(movieService: MovieService): MovieRemoteDatasource {
    return MovieRemoteDatasourceImpl(movieService = movieService)
  }
}