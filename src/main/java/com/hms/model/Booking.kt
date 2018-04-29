package com.hms.model

import org.hibernate.annotations.Proxy
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "BOOKING")
@Proxy(lazy = false)
class Booking : Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    var user: User? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_ID", nullable = true)
    var room: Room? = null

    @Column(name = "PEOPLE", nullable = false)
    var people: Int? = null

    @Column(name = "ARRIVAL_TIME", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    var arrivalTime: Date? = null

    @Column(name = "DEPARTURE_TIME", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    var departureTime: Date? = null

    @Column(name = "COMMENT", nullable = true)
    var comment: String? = null

    @Column(name = "STATUS", nullable = false)
    var status: String? = null

    @Column(name = "ROOM_BOOKED")
    var roomBooked: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (other !is Booking)
            return false
        val oth = other as Booking?
        if (id == null) {
            if (oth!!.id != null)
                return false
        } else if (id != oth!!.id)
            return false
        return if (room == null) {
            oth.room == null
        } else
            room == oth.room
    }

    override fun toString(): String {
        return "Booking [id=" + id + ", user=" + user + ", room=" + room + ", people=" + people +
                ", arrivalTime=" + arrivalTime + ", departureTime=" + departureTime + ", comment=" + comment +
                ", status=" + status + ", roomBooked=" + roomBooked + "]"
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (room?.hashCode() ?: 0)
        result = 31 * result + (people ?: 0)
        result = 31 * result + (arrivalTime?.hashCode() ?: 0)
        result = 31 * result + (departureTime?.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (roomBooked?.hashCode() ?: 0)
        return result
    }
}
