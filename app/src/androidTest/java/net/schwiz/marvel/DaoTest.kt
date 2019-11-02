package net.schwiz.marvel

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.io.IOException
import net.schwiz.marvel.data.db.MarvelDAO
import net.schwiz.marvel.data.db.MarvelDatabase
import net.schwiz.marvel.data.vo.*
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class DaoTest : KoinTest {

    private val marvelDatabase : MarvelDatabase by inject()
    private val marvelDAO : MarvelDAO by inject()


    @After
    @Throws(IOException::class)
    fun closeDb(){
        marvelDatabase.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun writeCharacterWithComicsAndRead(){
//        val character = Character(
//            id = 1,
//            name = "Spiderman",
//            description = "Websliger",
//            modified = "today",
//            resourceURI = "https://...",
//            thumbnail = null,
//            comics = null,
//            stories = null,
//            events = null,
//            series = null,
//            urls = null
//        )
//        val comic = Comic(
//            id = 1,
//            digitalId = 2,
//            title = "Amazing Spiderman",
//            issueNumber = 400,
//            variantDescription = null,
//            description = "Spiderman fights Venom",
//            modified = "today",
//            isbn = "",
//            upc = "",
//            diamondCode = "",
//            ean = "",
//            issn = "",
//            format = "",
//            pageCount = 25,
//            resourceURI = "",
//            thumbnail = null,
//            characters = null,
//            stories = null,
//            events = null,
//            series = null,
//            creators = null,
//            images = null
//        )
//
//        val characterComics = CharacterComics(character.id, listOf(comic))
//
//        runBlocking {
//
//            marvelDAO.insertCharacter(character)
//            marvelDAO.insertCharacterComics(characterComics)
//
//            val characterComicsById = CharacterComics(character.id, listOf(comic))
//            Assert.assertThat(characterComicsById, CoreMatchers.equalTo(characterComics))
//
//            val comicsById = marvelDAO.getCharacterComics(character.id).first().first()
//            Assert.assertThat(comicsById, CoreMatchers.equalTo(characterComics))
//
//            val comicCharacters = ComicCharacters(comic.id, listOf(character))
//            val charsById = marvelDAO.getComicCharacters(comic.id).first().first()
//            Assert.assertThat(charsById, CoreMatchers.equalTo(comicCharacters))
//        }
//    }
}