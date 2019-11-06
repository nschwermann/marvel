package net.schwiz.marvel.di

import net.schwiz.marvel.BuildConfig
import net.schwiz.marvel.ui.characters_list.CharactersListViewModel
import net.schwiz.marvel.util.AppCoroutineDispatchers
import net.schwiz.marvel.util.CoroutineDispatchers
import net.schwiz.marvel.util.Cthaeh
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

val appModule = module {

    single {
        when(BuildConfig.DEBUG){
            true -> Timber.DebugTree()
            false -> Cthaeh()
        }
    }

    single{
        AppCoroutineDispatchers() as CoroutineDispatchers
    }

    viewModel {
        CharactersListViewModel()
    }

}