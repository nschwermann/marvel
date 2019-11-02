package net.schwiz.marvel.data.db

import androidx.paging.DataSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.schwiz.marvel.data.vo.*

@Dao
interface MarvelDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters : List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Transaction
    suspend fun insertCharacterComics(characterComics: CharacterComics) {
        insertComics(characterComics.comics)
        characterComics.comics.map {
            CharacterToComic(characterComics.id, it.id)
        }.also {
            insertCharacterToComics(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(comics : List<Comic>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterToComics(characterToComics: List<CharacterToComic>)

    @Query("SELECT * FROM Character ORDER BY name")
    fun getCharacters() : DataSource.Factory<Int, Character>

    @Query("SELECT * FROM character WHERE character_id = :charId")
    fun getCharacter(charId : Int) : Flow<List<Character>>

    @Query("SELECT * FROM Character WHERE name LIKE :query")
    fun getCharacter(query: String) : Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM Character WHERE character_id = :charId")
    fun getCharacterComics(charId: Int) : Flow<List<CharacterComics>>

    @Delete
    suspend fun deleteCharacter(character: Character)

    @Delete
    suspend fun deleteCharacters(characters : List<Character>)

    @Transaction
    @Query("SELECT * FROM Comic WHERE comic_id = :comicId")
    fun getComicCharacters(comicId : Int) : Flow<List<ComicCharacters>>
}

