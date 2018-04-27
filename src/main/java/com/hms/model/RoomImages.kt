package com.hms.model

import org.hibernate.annotations.Proxy
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "ROOM_IMAGE")
@Proxy(lazy = false)
class RoomImages {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM", nullable = false)
    var room: Room? = null

    @Column(name = "NAME", nullable = false)
    var name: String? = null

    @Column(name = "URL", nullable = false)
    var url: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (other !is RoomImages)
            return false
        val oth = other as RoomImages?
        if (id == null) {
            if (oth!!.id != null)
                return false
        } else if (id != oth!!.id)
            return false
        return if (room == null) {
            oth.room == null
        } else
            room == other.room
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (id == null) 0 else id!!.hashCode()
        result = prime * result + if (name == null) 0 else name!!.hashCode()
        return result
    }

    override fun toString(): String {
        return "RoomImage [id=$id, room=$room, name=$name]"
    }
}
