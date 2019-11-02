package net.schwiz.marvel.data.vo

import androidx.room.*

@Entity(
    tableName = "character_comic_join",
    primaryKeys = ["character_id", "comic_id"],
    foreignKeys = [
        ForeignKey(entity = Character::class,
            parentColumns = ["character_id"],
            childColumns = ["character_id"]),
        ForeignKey(entity = Comic::class,
            parentColumns = ["comic_id"],
            childColumns = ["comic_id"])
    ]
)
data class CharacterToComic(
    @ColumnInfo(name = "character_id", index = true) val characterId : Int,
    @ColumnInfo(name = "comic_id", index = true) val comicId :Int
)
