package com.miguel.moviesapp.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun intArrayToJson(value: Array<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToIntArray(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList().toTypedArray()

    @TypeConverter
    fun stringArrayToJson(value: Array<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringArray(value: String) = Gson().fromJson(value, Array<String>::class.java).toList().toTypedArray()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}