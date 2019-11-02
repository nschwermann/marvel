package net.schwiz.marvel.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Story(
    @PrimaryKey val id : Int,
    val title : String,
    val description : String,
    val image : Image?,
    @Embedded(prefix = "comics_") val comics : ResourceList?,
    @Embedded(prefix = "series_") val series : ResourceList?,
    @Embedded(prefix = "characters_") val characters : ResourceList?,
    @Embedded(prefix = "creators_") val creators : ResourceList?,
    @Embedded(prefix = "comic_") val originalIssue : Comic
)