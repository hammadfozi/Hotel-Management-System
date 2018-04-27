package com.hms.model

import org.hibernate.validator.constraints.SafeHtml
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USER_PROFILE")
class UserProfile : Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "TYPE", length = 15, unique = true, nullable = false)
    var type: String? = UserProfileType.USER.userProfileType

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (id == null) 0 else id!!.hashCode()
        result = prime * result + if (type == null) 0 else type!!.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (other !is UserProfile)
            return false
        val oth = other as UserProfile?
        if (id == null) {
            if (oth!!.id != null)
                return false
        } else if (id != oth!!.id)
            return false
        if (type == null) {
            if (oth.type != null)
                return false
        } else if (type != oth.type)
            return false
        return true
    }

    override fun toString(): String {
        return "UserProfile [id=$id, type=$type]"
    }

}
