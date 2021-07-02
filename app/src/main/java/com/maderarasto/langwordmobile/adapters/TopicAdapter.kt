package com.maderarasto.langwordmobile.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.maderarasto.langwordmobile.MainActivity
import com.maderarasto.langwordmobile.R
import com.maderarasto.langwordmobile.models.Topic

class TopicAdapter(private val context: Context, private val dataSource: ArrayList<Topic>) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.list_item_topic, parent, false)
        val topic: Topic = getItem(position) as Topic

        val textTopicName: TextView = view.findViewById(R.id.text_topic_name)

        textTopicName.text = topic.name

        return view
    }
}