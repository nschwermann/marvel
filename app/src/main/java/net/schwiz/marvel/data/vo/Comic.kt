package net.schwiz.marvel.data.vo

import androidx.room.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Comic(
    @PrimaryKey @ColumnInfo(name="comic_id") val id : Int,
    val digitalId : Int,
    val title : String,
    val issueNumber : Int,
    val variantDescription : String?,
    val description : String,
    val modified : String,//TODO Date
    val isbn : String,
    val upc : String,
    val diamondCode : String,
    val ean : String,
    val issn : String,
    val format : String,
    val pageCount : Int,
    val resourceURI : String,
    @Embedded(prefix = "series_") val series : Series? = null,
    val thumbnail : Image?,
    val images : List<Image>?,
    @Embedded(prefix = "creators_") val creators : ResourceList?,
    @Embedded(prefix = "stories_") val stories : ResourceList?,
    @Embedded(prefix = "characters_") val characters : ResourceList?,
    @Embedded(prefix = "events_") val events : ResourceList?
)