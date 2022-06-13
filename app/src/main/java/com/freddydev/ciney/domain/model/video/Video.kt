package com.freddydev.ciney.domain.model.video

data class Video(
  val id: String,
  val key: String,
  val name: String,
  val official: Boolean,
  val published_at: String,
  val site: String,
  val size: Int,
  val type: String
)