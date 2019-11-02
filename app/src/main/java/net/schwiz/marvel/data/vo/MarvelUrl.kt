package net.schwiz.marvel.data.vo

import kotlinx.serialization.Serializable

@Serializable
data class MarvelUrl(val type : String, val url : String) //TODO switch to type enum

/*
types detail, wiki, purchase, reader, inAppLink, comiclink
 */