package com.mtjin.nomoneytrip.data.database.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class MyTypeConverters {
    @TypeConverter
    fun stringListToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun floatListToJson(value: List<Float>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToFloatList(value: String) = Gson().fromJson(value, Array<Float>::class.java).toList()
}