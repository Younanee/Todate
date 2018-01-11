package com.example.nyoun_000.todateproject

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog_diary.view.*

/**
 * Created by nyoun_000 on 2018-01-03.
 */
class DiaryDialogFragment : DialogFragment() {
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_diary, null)
        //Here, use findViewId()!!!
        view.tv_diary_title.tex


        builder.setView(view)
        builder.setTitle(mParam1.toString())
        builder.setPositiveButton("닫기", { dialog, which -> dialog.cancel()})
        builder.setNegativeButton("수정하기", { dialog, which ->  })

        return builder.create()
    }
//
//    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val date = view!!.findViewById<TextView>(R.id.tv_diary_date)
//        date.text = "hahaha!!!"
//
//    }

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
        fun newInstance(param1: String, param2: String): DialogFragment {
            val fragment = DiaryDialogFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args

            return fragment
        }
    }

}