package com.example.nyoun_000.todateproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*


///**
// * A simple [Fragment] subclass.
// * Use the [CalendarFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
class CalendarFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_calendar, container, false)
//    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val mCalendar = view?.findViewById<View>(R.id.mCalendar) as CalendarView



        mCalendar.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            DiaryDialogFragment.newInstance(year.toString() + ". " + (month+1).toString() + ". " + dayOfMonth.toString(), arrayListOf()).show(childFragmentManager, "MyDiaryDialogFragment")


            tv_testbox.setText(year.toString() + ". " + (month+1).toString() + ". " + dayOfMonth.toString())
        })
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
        fun newInstance(param1: String, param2: String): CalendarFragment {
            val fragment = CalendarFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
