package net.schwiz.marvel.data.vo

import androidx.room.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Character(
    @PrimaryKey @ColumnInfo(name = "character_id") val id : Int,
    val name : String,
    val description : String,
    val modified : String,//TODO LocalDateTime,
    val resourceURI : String,
    val thumbnail : Image? = null,
    @Embedded(prefix = "comics_") val comics : ResourceList?,
    @Embedded(prefix = "stories_") val stories : ResourceList?,
    @Embedded(prefix = "events_") val events : ResourceList?,
    @Embedded(prefix = "series_") val series : ResourceList?,
    val urls : List<MarvelUrl>?
)