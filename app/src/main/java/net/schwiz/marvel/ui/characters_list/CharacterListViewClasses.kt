package net.schwiz.marvel.ui.characters_list

import androidx.paging.PagedList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import net.schwiz.marvel.core.UIEvent
import net.schwiz.marvel.core.UIState
import net.schwiz.marvel.data.vo.Character
import net.schwiz.marvel.domain.DomainError

data class CharacterListViewState(
    val characters : PagedList<Character>? = null,
    val fetchError : DomainError? = null,
    val fetchingMoreCharacters : Boolean = false
) : UIState

sealed class CharacterListEvents : UIEvent{
    data class RequestCharacters(val nameStartsWith : String?) : CharacterListEvents()
}