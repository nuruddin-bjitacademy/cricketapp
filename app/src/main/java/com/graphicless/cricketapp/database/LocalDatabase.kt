package com.graphicless.cricketapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.graphicless.cricketapp.model.Continent
import com.graphicless.cricketapp.model.Country
import com.graphicless.cricketapp.model.League
import com.graphicless.cricketapp.temp.*

@Database(
    entities = [Continent::class, Country::class, League::class, FixturesIncludeRuns.Data::class, Teams.Data::class, Venues.Data::class, Stages.Data::class, Seasons.Data::class, FixturesIncludeRuns.Data.Run::class, Officials.Data::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
//@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun cricketDao(): CricketDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun instance(context: Context): LocalDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "cricket_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}