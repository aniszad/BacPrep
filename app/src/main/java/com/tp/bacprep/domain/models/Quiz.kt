package com.tp.bacprep.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quiz (
    var questions  : List<Triple<String, List<String>?, String>>,
    var time : Long,
    var type : HashMap<String, String>,
        ) : Parcelable