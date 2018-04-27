package com.hms.model

import org.hibernate.validator.constraints.SafeHtml
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "ROOM_TYPE")
class RoomType : Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @NotNull
    @SafeHtml
    @Column(name = "BASE_PRICE")
    var basePrice: Int? = null

    @Column(name = "TYPE", length = 15, unique = true, nullable = false)
    var type: String? = RoomTypeClass.FAMILY.roomTypeClass

    @OneToMany(mappedBy = "type")
    var rooms: Set<Room> = HashSet()

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
        if (other !is RoomType)
            return false
        val oth = other as RoomType?
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
        return "RoomType [id=$id, basePrice=$basePrice, type=$type]"
    }

}
