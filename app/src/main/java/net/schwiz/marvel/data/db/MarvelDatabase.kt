package net.schwiz.marvel.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.schwiz.marvel.data.vo.*

@Database(
    entities = [
        Character::class,
        Comic::class,
        Creator::class,
        Event::class,
        Series::class,
        Story::class,
        CharacterToComic::class
    ], version = 1)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun getMarvelDAO() : MarvelDAO
}