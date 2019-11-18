package net.schwiz.marvel.domain

import androidx.paging.PagedList
import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.schwiz.marvel.core.Action
import net.schwiz.marvel.core.Result
import net.schwiz.marvel.core.UseCase
import net.schwiz.marvel.data.repo.MarvelRepo
import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.util.CoroutineDispatchers
import org.koin.core.inject

class FetchCharactersUseCase(val scope : CoroutineScope) : UseCase<CharacterListActions, CharacterListResults>(){

    private val marvelRepo : MarvelRepo by inject()
    private val dispatchers : CoroutineDispatchers by inject()

    override fun transformActions(actions: Flow<CharacterListActions>): Flow<CharacterListResults> {
        return channelFlow{
            actions.collect { action ->
                when(action){
                    is CharacterListActions.FetchCharacters -> {
                        send(CharacterListResults.FetchingCharacters)
                        launch {
                            marvelRepo.getCharacters(action.nameStartsWith)
                                .onEach {
                                    send(CharacterListResults.FetchComplete(it))
                                }
                                .flowOn(dispatchers.io)
                                .collect()
                        }
                    }
                }
            }
        }
        .catch {
            emit(CharacterListResults.FetchComplete(Either.left(DomainError.UnknownError(cause = it))))
        }
    }
}

sealed class CharacterListActions : Action {
    data class FetchCharacters(val nameStartsWith : String?) : CharacterListActions()
}

sealed class CharacterListResults : Result {
    object FetchingCharacters : CharacterListResults()
    data class FetchComplete(val either : Either<DomainError, PagedList<Character>>) : CharacterListResults()
}