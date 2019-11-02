package net.schwiz.marvel.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Creator(
    @PrimaryKey val id : Int,
    val firstName : String,
    val middleName : String,
    val lastName : String,
    val suffix : String,
    val fullName : String,
    @Embedded val thumbnail : Image? = null,
    @Embedded(prefix = "series_") val series : ResourceList?,
    @Embedded(prefix = "stories_") val stories : ResourceList?,
    @Embedded(prefix = "comics_") val comics : ResourceList?,
    @Embedded(prefix = "events_") val events : ResourceList?
)