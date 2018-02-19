package com.example.nyoun_000.todateproject.DB

/**
 * Created by nyoun_000 on 2018-01-18.
 */

data class DiaryData(
                     val title : String = "No title",
                     val date : String = "No date",
                     val weather : String = "No weather",
                     val content : String = "No content",
                     val year : String = "No year",
                     val month : String = "No month",
                     val day : String = "No day"
                     ){
    companion object {
        val DB_NAME = "Todate.db"
        val DB_VERSION = 1
    }

    object DiaryTable{
        val TABLENAME : String = "diary_table"
        val _ID : String = "_id"
        val TITLE : String = "title"
        val DATE : String = "date"
        val WEATHER : String = "weather"
        val CONTENT : String = "content"
        val YEAR : String = "year"
        val MONTH : String = "month"
        val DAY : String = "day"

    }

}