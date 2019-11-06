package net.schwiz.marvel.data.repo

import androidx.paging.Config
import androidx.paging.PagedList
import arrow.core.Either
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.schwiz.marvel.data.FlowPagedListBuilder
import net.schwiz.marvel.data.api.MarvelService
import net.schwiz.marvel.data.db.MarvelDAO
import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.domain.DomainError
import timber.log.Timber
import java.io.File

interface MarvelRepo {

    fun getCharacters(nameStartsWith : String?) : Flow<Either<DomainError, PagedList<Character>>>
}

internal class MarvelRepoImpl(private val marvelService: MarvelService,
                              private val marvelDAO: MarvelDAO) : MarvelRepo{

    companion object{
        private const val PAGE_SIZE = 50
    }

    override fun getCharacters(nameStartsWith: String?) : Flow<Either<DomainError, PagedList<Character>>> {
        val dbSource = if(nameStartsWith != null) marvelDAO.getCharacter(nameStartsWith) else marvelDAO.getCharacters()
        return channelFlow {
            val callback = object : PagedList.BoundaryCallback<Int>(){

                override fun onZeroItemsLoaded() {
                    Timber.d("onZeroItemsLoaded")
                    launch {
                        marvelService.characters(PAGE_SIZE, 0, nameStartsWith).let {
                            val body = it.body()
                            if(it.isSuccessful && body != null) marvelDAO.insertCharacters(body.data.results)
                            else send(Either.left(DomainError.UnknownError(message = "")))
                        }
                        Timber.d("EndLaunch")
                    }
                }

                override fun onItemAtEndLoaded(itemAtEnd: Int) {
                    Timber.d("Load more characters starting at $itemAtEnd")
                    launch {
                        marvelService.characters(PAGE_SIZE, itemAtEnd, nameStartsWith).let {
                            val body = it.body()
                            if(it.isSuccessful && body != null) marvelDAO.insertCharacters(body.data.results)
                            else send(Either.left(DomainError.UnknownError(message = "")))
                            Timber.d("More Characters Loaded")
                        }
                    }
                }

            }
            FlowPagedListBuilder(dbSource, Config(pageSize = PAGE_SIZE)).apply {
                this.boundaryCallback = callback
            }.buildFlow().collect {
                send(Either.right(it))
            }

            awaitClose()
        }
    }

}