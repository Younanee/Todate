package com.example.nyoun_000.todateproject

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.example.nyoun_000.todateproject.DB.DBManagerDiary
import kotlinx.android.synthetic.main.dialog_diary.view.*

/**
 * Created by nyoun_000 on 2018-01-03.
 */
class DiaryDialogFragment : DialogFragment() {
    private var mParam1: String? = null
    private var mParam2: ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getStringArrayList(ARG_PARAM2)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_diary, null)
        //DBManagerDiary.init(activity) //디비를 굳이 열어야할까?? 그냥 매개변수를 통해 데이터 전달받아보기.

        //Here, use findViewId()!!!

        // mParam1 값은 xxxx년 xx월 xx일
        // mParam2 값들 = [0] 제목 , [1] 날짜 , [2] 날씨 , [3] 내용

        builder.setView(view)
        builder.setTitle(mParam2!![0])
        view.tv_diary_date.text = mParam1
        view.tv_diary_weather.text = mParam2!![2]
        view.tv_diary_content.text = mParam2!![3]
        builder.setPositiveButton("닫기", { dialog, which -> dialog.cancel()})
        //To Do 사진 업로드 방법 알기.
        //To Do 수정기능 넣기!
        builder.setNegativeButton("수정하기", { dialog, which ->  })

        return builder.create()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: ArrayList<String>): DialogFragment {
            val fragment = DiaryDialogFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putStringArrayList(ARG_PARAM2, param2)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onStop() {
        super.onStop()
        DBManagerDiary.db_close()
    }
}