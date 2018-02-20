package com.example.nyoun_000.todateproject.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*
import java.util.*

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

    fun getDiaryOfSelectedDateWithCursor(date: String) : Cursor =
            mDBHandler?.readableDatabase!!.query(DiaryData.DiaryTable.TABLENAME,
                    arrayOf(DiaryData.DiaryTable._ID,
                            DiaryData.DiaryTable.TITLE,
                            DiaryData.DiaryTable.DATE,
                            DiaryData.DiaryTable.WEATHER,
                            DiaryData.DiaryTable.CONTENT,
                            DiaryData.DiaryTable.YEAR,
                            DiaryData.DiaryTable.MONTH,
                            DiaryData.DiaryTable.DAY),
                    DiaryData.DiaryTable.DATE + "=?", arrayOf(date), null, null, null)

    fun getAllDateWithCursor() : Cursor =
            mDBHandler?.readableDatabase!!.query(DiaryData.DiaryTable.TABLENAME,
                    arrayOf(DiaryData.DiaryTable._ID,
                            DiaryData.DiaryTable.YEAR,
                            DiaryData.DiaryTable.MONTH,
                            DiaryData.DiaryTable.DAY),
                    null,null,null,null, DiaryData.DiaryTable._ID + " desc")


    fun getAllDiaryWithCursor() : Cursor =
            mDBHandler?.readableDatabase!!.query(DiaryData.DiaryTable.TABLENAME,
                    arrayOf(DiaryData.DiaryTable._ID,
                            DiaryData.DiaryTable.DATE,
                            DiaryData.DiaryTable.WEATHER,
                            DiaryData.DiaryTable.TITLE,
                            DiaryData.DiaryTable.CONTENT),
                    null,null,null,null, null)

    fun addDiaryData(data : DiaryData){
        val cv = ContentValues()
        cv.put(DiaryData.DiaryTable.TITLE, data.title)
        cv.put(DiaryData.DiaryTable.DATE, data.date)
        cv.put(DiaryData.DiaryTable.WEATHER, data.weather)
        cv.put(DiaryData.DiaryTable.CONTENT, data.content)
        cv.put(DiaryData.DiaryTable.YEAR, data.year)
        cv.put(DiaryData.DiaryTable.MONTH, data.month)
        cv.put(DiaryData.DiaryTable.DAY, data.day)
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
                Pair(DiaryData.DiaryTable.CONTENT, TEXT),
                Pair(DiaryData.DiaryTable.YEAR, TEXT),
                Pair(DiaryData.DiaryTable.MONTH, TEXT),
                Pair(DiaryData.DiaryTable.DAY, TEXT)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DiaryData.DiaryTable.TABLENAME, true)
        onCreate(db)
    }
}