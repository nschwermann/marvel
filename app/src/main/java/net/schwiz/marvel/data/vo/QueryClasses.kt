package net.schwiz.marvel.data.vo

import androidx.room.*

data class CharacterComics(
    @ColumnInfo(name = "character_id") val id : Int,
    @Relation(
        parentColumn = "character_id",
        entity = Comic::class,
        entityColumn = "comic_id",
        associateBy = Junction(CharacterToComic::class)
    )
    val comics : List<Comic>
)

data class ComicCharacters(
    @ColumnInfo(name = "comic_id") val id : Int,
    @Relation(
        parentColumn = "comic_id",
        entityColumn = "character_id",
        entity = Character::class,
        associateBy = Junction(CharacterToComic::class)
    )
    val characters : List<Character>
)