package com.magdales.lastfmfinalsexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Lai on 3/21/2018.
 */

data class Album(val name: String,
                 val artist: String,
                 @SerializedName("#text")
                 val text: String)