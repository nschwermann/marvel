package net.schwiz.marvel.data.api

import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.data.vo.Comic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    companion object{
        const val END_POINT = "https://gateway.marvel.com:443/v1/public/"
    }

    @GET("characters?orderBy=name")
    suspend fun characters(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value ="nameStartsWith", encoded = true) name : String? = null) : Response<Result<Character>>

    @GET("characters/{id}")
    suspend fun characterById(@Path("id")id : Int) : Response<Result<Character>>

    @GET("characters/{id}/comics?orderBy=title")
    suspend fun getCharacterComics(@Path(value="id") id : Int,
                                   @Query(value = "limit") limit : Int,
                                   @Query(value = "offset") offset : Int) : Response<Result<Comic>>
}