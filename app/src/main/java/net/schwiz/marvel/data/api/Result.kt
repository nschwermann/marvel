package net.schwiz.marvel.data.api

import kotlinx.serialization.Serializable

@Serializable
data class Result<T>(
    val code : Int,
    val status : String,
    val data : Container<T>,
    val etag : String,
    val copyright : String,
    val attributionText : String,
    val attributionHTML : String
)