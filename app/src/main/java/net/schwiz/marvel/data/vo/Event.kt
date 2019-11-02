package net.schwiz.marvel.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Event(
    @PrimaryKey val id : Int,
    val title : String,
    val description : String,
    @Embedded val thumbnail : Image?,
    @Embedded(prefix = "comics_") val comics : ResourceList?,
    @Embedded(prefix = "stories_") val stories : ResourceList?,
    @Embedded(prefix = "series_") val series : ResourceList?,
    @Embedded(prefix = "characters_") val characters : ResourceList?,
    @Embedded(prefix = "creators_") val creators : ResourceList?
)