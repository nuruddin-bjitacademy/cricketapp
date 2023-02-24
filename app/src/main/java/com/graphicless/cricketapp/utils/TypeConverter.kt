package com.graphicless.cricketapp.utils

import androidx.room.TypeConverter
import com.graphicless.cricketapp.Model.CurrentPlayers
import com.graphicless.cricketapp.Model.PlayerAll

class TypeConverter {
    @TypeConverter
    fun fromPosition(position: PlayerAll.Data.Position): String{
        return position.name.toString()
    }

    @TypeConverter
    fun toPosition(name: String):  PlayerAll.Data.Position{
        return PlayerAll.Data.Position("position", 0, name)
    }


    @TypeConverter
    fun fromCurrentPlayerPosition(position: CurrentPlayers.Data.Squad.Position): String{
        return position.name.toString()
    }

    @TypeConverter
    fun toCurrentPlayerPosition(name: String): CurrentPlayers.Data.Squad.Position{
        return CurrentPlayers.Data.Squad.Position("position", 0, name)
    }

//    @TypeConverter
//    fun listToJson(value: List<FixturesIncludeRuns.Data.Run?>) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<FixturesIncludeRuns.Data.Run>::class.java).toList()
}