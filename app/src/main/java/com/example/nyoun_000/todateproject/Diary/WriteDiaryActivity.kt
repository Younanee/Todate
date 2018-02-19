package com.example.nyoun_000.todateproject.Diary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nyoun_000.todateproject.DB.DBManagerDiary
import com.example.nyoun_000.todateproject.DB.DiaryData
import com.example.nyoun_000.todateproject.R
import kotlinx.android.synthetic.main.activity_write_diary.*

class WriteDiaryActivity : AppCompatActivity() {
    init {
        DBManagerDiary.init(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary)

        var intent = intent
        var selectedYear = intent.extras.getString("selectedYear")
        var selectedMonth = intent.extras.getString("selectedMonth")
        var selectedDay = intent.extras.getString("selectedDay")

        bt_submit.setOnClickListener {
            val data = DiaryData(
                    et_title.text.toString(),
                    selectedYear+selectedMonth+selectedDay,
                    et_weather.text.toString(),
                    et_content.text.toString(),
                    selectedYear,
                    selectedMonth,
                    selectedDay)
            DBManagerDiary.addDiaryData(data)
            finish()
        }
        bt_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        DBManagerDiary.db_close()
    }
}
