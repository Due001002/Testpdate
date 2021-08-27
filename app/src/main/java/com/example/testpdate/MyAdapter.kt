package com.example.testpdate

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MyAdapter(val items: MutableList<String>) : RecyclerView.Adapter<ViewHolder>(){

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cardview_recyclerview, parent, false),mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    var txtviewNameTitle = holder.itemView.findViewById<TextView>(R.id.textviewName_RecycleEndGame)
    var txtviewPointTitle = holder.itemView.findViewById<TextView>(R.id.textviewPoint_RecyclerEndGame)
//        txtviewNameTitle.text = items[position].get("name").toString()
//        txtviewPointTitle.text = items[position].get("point").toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
class MyViewHolder(view: View, listener: MyAdapter.onItemClickListener) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}
/*class MyAdapter(val items: Array<String>, val imageId: Array<Int>, val context: Context) : RecyclerView.Adapter<ViewHolder>()
{
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        p0?.getNameTxt?.text = items.get(p1)
        p0?.getThumbnail?.setImageResource(imageId.get(p1))
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.model, p0, false))
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val getNameTxt = view.nameTxt
    val getThumbnail = view.thumbnail
}*/