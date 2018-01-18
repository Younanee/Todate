package com.example.nyoun_000.todateproject.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*

/**
 * Created by nyoun_000 on 2018-01-18.
 */
object DBManagerDiary {
    private var mDBHandler : DBHandlerDiary? = null

    fun init(context: Context){
        if (mDBHandler == null) {
            mDBHandler = DBHandlerDiary(context)
        }
    }

    fun addDiaryData(data : DiaryData){
        val cv = ContentValues()
        cv.put(DiaryData.DiaryTable.DATE, data.date)
        cv.put(DiaryData.DiaryTable.TITLE, data.title)
        cv.put(DiaryData.DiaryTable.WEATHER, data.weather)
        cv.put(DiaryData.DiaryTable.CONTENT, data.content)
        mDBHandler?.writableDatabase.use {
            mDBHandler?.writableDatabase?.insert(
                    DiaryData.DiaryTable.TABLENAME , null, cv)
        }
    }

    fun db_close(){
        mDBHandler?.close()
    }
}

class DBHandlerDiary(context: Context) : SQLiteOpenHelper(context, DiaryData.DB_NAME, null, DiaryData.DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                DiaryData.DiaryTable.TABLENAME,
                true,
                Pair(DiaryData.DiaryTable._ID, INTEGER + PRIMARY_KEY),
                Pair(DiaryData.DiaryTable.TITLE, TEXT),
                Pair(DiaryData.DiaryTable.DATE, TEXT),
                Pair(DiaryData.DiaryTable.WEATHER, TEXT),
                Pair(DiaryData.DiaryTable.CONTENT, TEXT)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DiaryData.DiaryTable.TABLENAME, true)
    }
}