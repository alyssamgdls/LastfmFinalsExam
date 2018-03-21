package com.magdales.lastfmfinalsexam

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Lai on 3/21/2018.
 */
class AlbumAdapter(val albumlist: ArrayList<AlbumDetails>) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return albumlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = albumlist[position].name
        holder.artistText.text = albumlist[position].artist
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById(R.id.textView_name) as TextView
        val artistText = itemView.findViewById(R.id.textView_artist) as TextView
    }

}