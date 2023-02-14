package com.graphicless.cricketapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.graphicless.cricketapp.temp.FixturesIncludeRuns

class TypeConverter {

    /*@TypeConverter
    fun fromSource(source: Source): String{
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }*/

    @TypeConverter
    fun listToJson(value: List<FixturesIncludeRuns.Data.Run?>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<FixturesIncludeRuns.Data.Run>::class.java).toList()
}