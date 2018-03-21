package com.magdales.lastfmfinalsexam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Created by Lai on 3/21/2018.
 */
class AlbumAdapter(var context: Context, val albumlist: ArrayList<Album>) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return albumlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.textView_name?.text = albumlist[position].name
        holder?.itemView?.textView_artist?.text = albumlist[position].artist
        val imgHolder = holder!!.itemView!!.imageView

        if (albumlist[position].text == "null") {
            Picasso.with(holder.itemView.context).load(R.drawable.ic_launcher_background).into(imgHolder)
        } else {
            Picasso.with(holder.itemView.context).load(albumlist[position].text).into(imgHolder)
        }

        Picasso.with(holder.itemView?.context).load(albumlist[position].text).into(imgHolder)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById(R.id.textView_name) as TextView
        val artistText = itemView.findViewById(R.id.textView_artist) as TextView
    }

}