package com.hms.model

import java.io.Serializable

enum class UserProfileType(userProfileType: String) : Serializable {
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    UNVERIFIED("UNVERIFIED");

    var userProfileType: String
        internal set

    init {
        this.userProfileType = userProfileType
    }

}
