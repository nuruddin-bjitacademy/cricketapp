package com.graphicless.cricketapp.temp

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam

class TypeConverter{
    @TypeConverter
    fun fromFixtureAndTeamList(value: List<FixtureAndTeam>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFixtureAndTeamList(value: String): List<FixtureAndTeam> {
        val type = object : TypeToken<List<FixtureAndTeam>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromRunList(value: List<FixturesIncludeRuns.Data.Run>): String {
        val gson = Gson()
        val type = object : TypeToken<List<FixturesIncludeRuns.Data.Run>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRunList(value: String): List<FixturesIncludeRuns.Data.Run> {
        val gson = Gson()
        val type = object : TypeToken<List<FixturesIncludeRuns.Data.Run>>() {}.type
        return gson.fromJson(value, type)
    }
}

