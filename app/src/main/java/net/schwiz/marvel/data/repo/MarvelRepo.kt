package net.schwiz.marvel.data.repo

import net.schwiz.marvel.data.api.MarvelService
import net.schwiz.marvel.data.db.MarvelDAO
import net.schwiz.marvel.util.ContextProviders

class MarvelRepo(
    private val marvelDAO: MarvelDAO,
    private val marvelService: MarvelService,
    private val contextProviders: ContextProviders
) {


}