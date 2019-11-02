package net.schwiz.marvel.data.api

import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.data.vo.Comic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface MarvelService {

    companion object{
        const val END_POINT = "https://gateway.marvel.com:443/v1/public/"
    }

    @GET("characters?orderBy=name")
    suspend fun characters(
        @QueryName limit : Int,
        @QueryName offset : Int,
        @Query(value ="nameStartsWith", encoded = true) name : String? = null) : Response<Result<Character>>

    @GET("characters/{id}")
    suspend fun characterById(@Path("id")id : Int) : Response<Result<Character>>

    @GET("characters/{id}/comics?orderBy=title")
    suspend fun getCharacterComics(@Path(value="id") id : Int,
                                   @QueryName limit : Int,
                                   @QueryName offest : Int) : Response<Result<Comic>>
}