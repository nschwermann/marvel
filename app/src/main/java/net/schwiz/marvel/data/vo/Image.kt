package net.schwiz.marvel.data.vo

import kotlinx.serialization.Serializable

@Serializable
data class Image(val path : String, val extension : String)