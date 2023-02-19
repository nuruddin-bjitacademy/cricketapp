package com.graphicless.cricketapp.utils

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.android.material.datepicker.CalendarConstraints


private const val TAG = "DateValidator"
class DateValidator : CalendarConstraints.DateValidator {

    private var timeStamp: List<Long>

    constructor(timeStamp: List<Long>){
        this@DateValidator.timeStamp = timeStamp
    }

    constructor(parcel: Parcel){
        timeStamp = listOf(parcel.readLong())
    }


    override fun isValid(date: Long): Boolean {

        Log.d(TAG, "isValid: $date")
        var v = false
        for(t in timeStamp.size-1 downTo 0){
            if(date == timeStamp[t]*1000){
                v = true
            }
        }

        return v
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DateValidator?> = object :
            Parcelable.Creator<DateValidator?> {
            override fun createFromParcel(parcel: Parcel): DateValidator? {
                return DateValidator(parcel)
            }

            override fun newArray(size: Int): Array<DateValidator?> {
                return arrayOfNulls(size)
            }
        }
    }
}