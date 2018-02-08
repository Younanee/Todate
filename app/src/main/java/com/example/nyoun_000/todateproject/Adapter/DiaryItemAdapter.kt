package com.example.nyoun_000.todateproject.Adapter

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nyoun_000.todateproject.R
import com.kotlin.phonebook.adapter.CursorRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_diary_list.view.*

/**
 * Created by nyoun_000 on 2018-02-03.
 */

class DiaryItemAdapter (context: Context, cursor: Cursor) : CursorRecyclerViewAdapter<DiaryItemAdapter.ViewHolder>(context, cursor) {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val mContext : Context = context
    private var onItemClick:View.OnClickListener? = null

    class ViewHolder(view : View, context: Context) : RecyclerView.ViewHolder(view){
        val mView = view
        val mContext = context
        fun bindView(cursor: Cursor){
            with(mView){
                tv_id.text = cursor.getString(0)
                tv_title.text = cursor.getString(3)
                tv_date.text = cursor.getString(1)
                tv_content.text = cursor.getString(4)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val mainView : View = mInflater.inflate(R.layout.item_diary_list, parent, false)
        return ViewHolder(mainView,mContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        holder.itemView.setOnClickListener(onItemClick)
        holder.bindView(cursor)
    }
}