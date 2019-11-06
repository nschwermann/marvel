package net.schwiz.marvel.di

import net.schwiz.marvel.domain.FetchCharactersUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        FetchCharactersUseCase()
    }
}