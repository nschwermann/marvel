package net.schwiz.marvel.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.schwiz.marvel.data.api.AuthInterceptor
import net.schwiz.marvel.data.api.MarvelService
import net.schwiz.marvel.data.db.MarvelDatabase
import net.schwiz.marvel.data.repo.MarvelRepo
import net.schwiz.marvel.data.repo.MarvelRepoImpl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.error.MissingPropertyException
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val dataModule = module {

    single {
        Room.inMemoryDatabaseBuilder(get(), MarvelDatabase::class.java).build()
    }

    single {
        get<MarvelDatabase>().getMarvelDAO()
    }

    single {
        Retrofit.Builder()
            .baseUrl(MarvelService.END_POINT)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
            .create(MarvelService::class.java)
    }

    single {
        try{
            AuthInterceptor(getProperty("private.key"))
        }catch(e : MissingPropertyException){
            Timber.e("You need an assets/koin.properties file containing private.key")
            throw e
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    single{
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single{
        MarvelRepoImpl(get(), get()) as MarvelRepo
    }
}

