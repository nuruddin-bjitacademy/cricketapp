package com.graphicless.cricketapp.Model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

data class PlayerAll(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Parcelize
    @Entity(tableName = "Player")
    data class Data(
        @Json(name = "resource")
        var resource: String? = null,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "country_id")
        var countryId: Int? = null,
        @Json(name = "firstname")
        var firstname: String? = null,
        @Json(name = "lastname")
        var lastname: String? = null,
        @Json(name = "fullname")
        var fullname: String? = null,
        @Json(name = "image_path")
        var imagePath: String? = null,
        @Json(name = "dateofbirth")
        var dateofbirth: String? = null,
        @Json(name = "gender")
        var gender: String? = null,
        @Json(name = "battingstyle")
        var battingstyle: String? = null,
        @Json(name = "bowlingstyle")
        var bowlingstyle: String? = null,
        @Json(name = "position")
        var position: @RawValue Position? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null
    ):Parcelable {
        data class Position(
            @Json(name = "resource")
            var resource: String? = null,
            @Json(name = "id")
            var id: Int? = null,
            @Json(name = "name")
            var name: String? = null
        )
    }
}