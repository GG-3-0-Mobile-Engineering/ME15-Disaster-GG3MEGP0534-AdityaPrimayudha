package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adapter(private val context: Context, private val bencanaList: List<Banjir>) : BaseAdapter() {
    override fun getCount(): Int {
        return bencanaList.size
    }

    override fun getItem(p0: Int): Any {
        return bencanaList[p0].result.objects.output.geometries[0].properties
    }

    override fun getItemId(p0: Int): Long {
        return bencanaList[p0].result.objects.output.geometries[0].properties.area_id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (p1 == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_list_view, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = p1
            viewHolder = view.tag as ViewHolder
        }

        val bencana = bencanaList[p0]
        viewHolder.bencanaTitle.text = bencana.result.objects.output.geometries[0].properties.area_name
        viewHolder.bencanaArea.text = bencana.result.objects.output.geometries[0].properties.city_name
        viewHolder.bencanaLastupdated.text = bencana.result.objects.output.geometries[0].properties.last_updated

        return view
    }
    private class ViewHolder(view: View) {
        val bencanaTitle: TextView = view.findViewById(R.id.bencanaTitle)
        val bencanaArea: TextView = view.findViewById(R.id.bencanaArea)
        val bencanaLastupdated: TextView = view.findViewById(R.id.bencanaLastUpdated)
    }
}