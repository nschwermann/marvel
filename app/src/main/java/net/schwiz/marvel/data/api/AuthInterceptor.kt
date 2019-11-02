package net.schwiz.marvel.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthInterceptor(private val privateKey : String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = Date().time.toString()
        val pubKey = "601fcffeabf68809a3a296a55cf7ee1e"
        val hash = "$ts$privateKey$pubKey".md5
        return chain.request().url.newBuilder()
            .addQueryParameter("apikey", pubKey)
            .addQueryParameter("ts", ts)
            .addQueryParameter("hash", hash)
            .build().run {
                chain.request().newBuilder().url(this).build()
            }.let {
                chain.proceed(it)
            }
    }
}