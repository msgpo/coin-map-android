package com.bubelov.coins.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.bubelov.coins.repository.place.PlacesDb
import com.bubelov.coins.model.Place

/**
 * @author Igor Bubelov
 */

@Database(entities = arrayOf(Place::class), version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun placesDb(): PlacesDb
}