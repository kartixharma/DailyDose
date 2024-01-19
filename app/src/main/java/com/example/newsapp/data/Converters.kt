package com.example.newsapp.data

import androidx.room.TypeConverter
import com.example.newsapp.network.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }
}