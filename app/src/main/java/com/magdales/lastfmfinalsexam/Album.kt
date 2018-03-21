package com.magdales.lastfmfinalsexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Lai on 3/21/2018.
 */

data class Results(val albumMatches: AlbumMatches)

data class AlbumMatches(val album: ArrayList<AlbumDetails>)

data class AlbumDetails(val name: String,
                 val artist: String,
                 val image: String)

data class Album(val result: Results)

data class Image(@SerializedName("#text") val text: String, val size: String)