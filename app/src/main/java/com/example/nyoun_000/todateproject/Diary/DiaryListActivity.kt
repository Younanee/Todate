package com.example.nyoun_000.todateproject.Diary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.nyoun_000.todateproject.Adapter.DiaryItemAdapter
import com.example.nyoun_000.todateproject.DB.DBManagerDiary
import com.example.nyoun_000.todateproject.R
import kotlinx.android.synthetic.main.activity_diary_list.*

class DiaryListActivity : AppCompatActivity() {
    var adapter : DiaryItemAdapter? = null
    init {
        DBManagerDiary.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_list)

        //리사이클 뷰 붙이기 작업
        rv_diary_list.layoutManager = LinearLayoutManager(this)
        val cursor = DBManagerDiary.getAllDiaryWithCursor()
        cursor.moveToFirst()
        adapter = DiaryItemAdapter(this, cursor)
        rv_diary_list.adapter = adapter

    }

    override fun onStop() {
        super.onStop()
        DBManagerDiary.db_close()
    }
}
