package net.schwiz.marvel.di

import kotlinx.coroutines.CoroutineScope
import net.schwiz.marvel.domain.FetchCharactersUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {(scope : CoroutineScope) ->
        FetchCharactersUseCase(scope)
    }
}