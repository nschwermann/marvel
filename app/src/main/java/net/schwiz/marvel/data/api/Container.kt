package net.schwiz.marvel.data.api

import kotlinx.serialization.Serializable

@Serializable
data class Container<T>(val offset: Int,
                        val limit: Int,
                        val total : Int,
                        val count : Int,
                        val results : List<T>)