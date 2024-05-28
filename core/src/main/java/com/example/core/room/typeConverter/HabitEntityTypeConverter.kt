package com.example.core.room.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class HabitEntityTypeConverter {
    @TypeConverter
    fun fromIntegerList(value: String?): List<Int>? {
        if (value == null) {
            return null
        }
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toIntegerList(list: List<Int>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }
}