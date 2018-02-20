package com.example.nyoun_000.todateproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.nyoun_000.todateproject.DB.DBManagerDiary
import com.example.nyoun_000.todateproject.Decorator.EventDecorator
import com.example.nyoun_000.todateproject.Decorator.OneDayDecorator
import com.example.nyoun_000.todateproject.Diary.DiaryListActivity
import com.example.nyoun_000.todateproject.Diary.WriteDiaryActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_init.*
import kotlinx.android.synthetic.main.app_bar_init.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class InitActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnDateSelectedListener {
    lateinit var calendarView : MaterialCalendarView
    init {
        DBManagerDiary.init(this)
    }


    companion object {
        val oneDayDecorator = OneDayDecorator()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        setSupportActionBar(toolbar)

        //달력달기
        calendarView = findViewById(R.id.calendarView)
        //calendarView.setSelectedDate(Calendar.getInstance().time) //오늘날짜에 빨간색으로 선택되는 로직
        //calendarView.addDecorators(oneDayDecorator) //오늘날에 굵은글씨
        ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor())

        //달력 날짜 클릭 리스너
        calendarView.setOnDateChangedListener { widget, date, selected ->
            //date에 대한 디비에 데이터가 있으면 일기보기
            var selectedDate : String = date.year.toString() + date.month.toString() + date.day.toString()
            var cursor = DBManagerDiary.getDiaryOfSelectedDateWithCursor(selectedDate)
            if (cursor.count != 0) {
                toast("커서 있음, 갯수 : " + cursor.count + ", 다이어로그 일기 보기 띄움")
                cursor.moveToFirst()
                var selectedDate = date.year.toString() + "년 "+ (date.month+1).toString() +"월 "+ date.day.toString() + "일"
                var data : ArrayList<String>
                        = arrayListOf(cursor.getString(1).toString(),
                        cursor.getString(2).toString(),
                        cursor.getString(3).toString(),
                        cursor.getString(4).toString())
                DiaryDialogFragment.newInstance(selectedDate, data).show(supportFragmentManager, "MyDiaryDialogFragment")
            } else {
                alert ("일기가 아직 없어요!!!"){
                    title = "일기를 쓰시겠습니까?~!"
                    positiveButton("쓸래요~!", {
                        val intent = Intent(applicationContext, WriteDiaryActivity::class.java)
                        intent.putExtra("selectedYear", date.year.toString())
                        intent.putExtra("selectedMonth", date.month.toString())
                        intent.putExtra("selectedDay", date.day.toString())
                        startActivity(intent)
                    })
                    negativeButton("안쓸래요~!", {
                        dialog -> dialog.cancel()
                    })
                }.show()
//                val intent = Intent(this, WriteDiaryActivity::class.java)
//                intent.putExtra("selectedYear", date.year.toString())
//                intent.putExtra("selectedMonth", date.month.toString())
//                intent.putExtra("selectedDay", date.day.toString())
//                startActivity(intent)
            }
            //데이터가 없으면 일기쓰기
        }


        //네비게이션 부분
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Fragment 셋팅
        //supportFragmentManager.beginTransaction().replace(R.id.place_mFragment, CalendarFragment.newInstance("","")).commit()



    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.init, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {
                val intent = Intent(this, WriteDiaryActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_slideshow -> {
                val intent = Intent(this, DiaryListActivity::class.java)
                startActivity(intent)

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private inner class ApiSimulator : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
//            try {
//                Thread.sleep(100)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
            //달력에 점 찍는 로직
            val calendar = Calendar.getInstance()
            //calendar.add(Calendar.MONTH, -2) //현재 날짜를 2달 전으로 이동
            val dates = ArrayList<CalendarDay>()
//            for (i in 0..29) {
//                val day = CalendarDay.from(calendar) //현재 날짜를 day변수에 넣움
//                dates.add(day) //해당 day에 점찍음
//                calendar.add(Calendar.DATE, 5)// 해당 day에 5만큼 일수 더함 ... 반복!!!
//            }

            var day : CalendarDay

            //모든 일기 캘린더에 점찍기
            var cursor = DBManagerDiary.getAllDateWithCursor()
            if(cursor.moveToFirst()){
                do {
                    day = CalendarDay.from(cursor.getString(1).toInt(), cursor.getString(2).toInt(), cursor.getString(3).toInt())
                    dates.add(day)
                    cursor.moveToNext()
                } while (!cursor.isLast)
            }


            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)

            if (isFinishing) {
                return
            }
            //점찍는 부분
            calendarView.addDecorator(EventDecorator(Color.RED, calendarDays))

            //테스트
//            var calendarDays2 = ArrayList<CalendarDay>()
//            calendarDays2.add(CalendarDay.from(Calendar.getInstance()))
//            calendarView.addDecorator(EventDecorator(Color.BLUE,calendarDays2))
        }
    }

    override fun onStop() {
        super.onStop()
        DBManagerDiary.db_close()
    }

    override fun onStart() {
        super.onStart()
        ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor())
    }
}
