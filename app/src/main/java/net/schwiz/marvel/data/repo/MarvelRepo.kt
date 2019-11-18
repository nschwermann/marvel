package net.schwiz.marvel.data.repo

import androidx.paging.Config
import androidx.paging.PagedList
import arrow.core.Either
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import net.schwiz.marvel.data.FlowPagedListBuilder
import net.schwiz.marvel.data.api.MarvelService
import net.schwiz.marvel.data.db.MarvelDAO
import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.domain.DomainError
import timber.log.Timber

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

        return channelFlow<Either<DomainError, PagedList<Character>>> {
            val callback = Callback(this, marvelService, marvelDAO, nameStartsWith, 0 )
            FlowPagedListBuilder(dbSource, Config(pageSize = PAGE_SIZE, prefetchDistance = 0)).apply {
                this.boundaryCallback = callback
            }.buildFlow().collectIndexed { index, value ->
                Timber.d("Collected page: $index")
                callback.page = (index * PAGE_SIZE) + 1
                send(Either.right(value))
            }

            awaitClose()
        }
    }

    private class Callback(val scope : ProducerScope<Either<DomainError, PagedList<Character>>>,
                           val marvelService: MarvelService,
                           val marvelDAO: MarvelDAO,
                           val nameStartsWith: String?,
                           var page : Int) : PagedList.BoundaryCallback<Character>(){

        override fun onZeroItemsLoaded() {
            scope.launch {
                marvelService.characters(PAGE_SIZE, 0, nameStartsWith).let {
                    val body = it.body()
                    if(it.isSuccessful && body != null){
                        marvelDAO.insertCharacters(body.data.results)
                    }
                    else {
                        scope.send(Either.left(DomainError.UnknownError(message = "")))
                    }
                }
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: Character) {
            Timber.d("Load more characters starting at $itemAtEnd")
            scope.launch {
                marvelService.characters(PAGE_SIZE, page, nameStartsWith).let {
                    val body = it.body()
                    if(it.isSuccessful && body != null) marvelDAO.insertCharacters(body.data.results)
                    else scope.send(Either.left(DomainError.UnknownError(message = "")))
                    Timber.d("More Characters Loaded")
                }
            }
        }
    }

}