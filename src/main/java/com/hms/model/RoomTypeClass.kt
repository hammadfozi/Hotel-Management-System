package com.hms.model

import java.io.Serializable

enum class RoomTypeClass private constructor(roomTypeClass: String) : Serializable {
    FAMILY("FAMILY"),
    DELUXE("DELUXE"),
    EXECUTIVE("EXECUTIVE");

    var roomTypeClass: String
        internal set

    init {
        this.roomTypeClass = roomTypeClass
    }
}