package com.freddydev.ciney.domain.model.review

import com.freddydev.ciney.data.dto.review.AuthorDetailDto

data class Review(
  val author: String,
  val author_details: AuthorDetailDto,
  val content: String,
  val created_at: String,
  val id: String,
  val updated_at: String,
  val url: String
)