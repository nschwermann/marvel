package net.schwiz.marvel.ui.characters_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.schwiz.marvel.core.BaseViewModel
import net.schwiz.marvel.domain.CharacterListActions
import net.schwiz.marvel.domain.CharacterListResults
import net.schwiz.marvel.domain.FetchCharactersUseCase
import org.koin.core.inject

class CharactersListViewModel : BaseViewModel<CharacterListEvents, CharacterListViewState, CharacterListActions, CharacterListResults>() {

    private val useCase : FetchCharactersUseCase by inject()

    override fun makeInitState(): CharacterListViewState = CharacterListViewState()

    override fun Flow<CharacterListEvents>.eventTransform(): Flow<CharacterListActions> = map {
        when(it){
            is CharacterListEvents.RequestCharacters -> CharacterListActions.FetchCharacters(it.nameStartsWith)
        }
    }

    override fun Flow<CharacterListActions>.actionTransform(): Flow<CharacterListResults> = useCase.transformActions(this)

    override fun CharacterListViewState.plus(next: CharacterListResults): CharacterListViewState =
        when(next){
        is CharacterListResults.FetchingCharacters -> copy(fetchingMoreCharacters = true)
        is CharacterListResults.FetchComplete -> {
            next.either.fold(
                {copy(fetchingMoreCharacters = false, fetchError = it)},
                {copy(fetchingMoreCharacters = false, characters = it, fetchError = null)}
            )
        }
    }
}
