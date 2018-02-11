package com.example.nyoun_000.todateproject

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
import com.example.nyoun_000.todateproject.Decorator.EventDecorator
import com.example.nyoun_000.todateproject.Decorator.HighlightWeekendsDecorator
import com.example.nyoun_000.todateproject.Decorator.MySelectorDecorator
import com.example.nyoun_000.todateproject.Decorator.OneDayDecorator
import com.example.nyoun_000.todateproject.Diary.DiaryListActivity
import com.example.nyoun_000.todateproject.Diary.WriteDiaryActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_init.*
import kotlinx.android.synthetic.main.app_bar_init.*
import java.util.*
import java.util.concurrent.Executors


class InitActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnDateSelectedListener {
    lateinit var calendarView : MaterialCalendarView

    companion object {
        val oneDayDecorator = OneDayDecorator()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        setSupportActionBar(toolbar)
        //달력달기
        calendarView = findViewById(R.id.calendarView)
        calendarView.setSelectedDate(Calendar.getInstance().time)

        calendarView.addDecorators(MySelectorDecorator(this), HighlightWeekendsDecorator(), oneDayDecorator)
        ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor())


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


//        //디비에 데이터넣는 작업 테스트 이동
//        bt_create_diary.setOnClickListener {
//            val intent = Intent(this, WriteDiaryActivity::class.java)
//            startActivity(intent)
//        }


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
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -2)
            val dates = ArrayList<CalendarDay>()
            for (i in 0..29) {
                val day = CalendarDay.from(calendar)
                dates.add(day)
                calendar.add(Calendar.DATE, 5)
            }

            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)

            if (isFinishing) {
                return
            }

            calendarView.addDecorator(EventDecorator(Color.RED, calendarDays))
        }
    }

}
