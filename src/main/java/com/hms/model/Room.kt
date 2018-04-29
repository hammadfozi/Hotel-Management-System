package com.hms.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Proxy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "ROOM")
@Proxy(lazy = false)
class Room : Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "NAME", nullable = false, unique = true)
    var name: String? = null

    @NotNull
    @Column(name = "PRICE", nullable = false)
    var price: Int? = null

    @NotNull
    @Column(name = "CAPACITY", nullable = false)
    var capacity: Int? = null

    @Column(name = "DESCRIPTION", nullable = true)
    var description: String? = null

    @NotNull
    @Column(name = "BATH", nullable = false)
    var bath: Boolean? = null

    @NotNull
    @Column(name = "BED", nullable = false)
    var bed: Int? = null

    @NotNull
    @Column(name = "INTERNET", nullable = false)
    var internet: Boolean? = null

    @Column(name = "STATUS")
    var status: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TYPE", nullable = false)
    var type: RoomType? = null

    @OneToOne(mappedBy = "room", fetch = FetchType.EAGER)
    var booking: Booking? = null

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var images: List<RoomImages> = ArrayList()

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (other !is Room)
            return false
        val oth = other as Room?
        if (id == null) {
            if (oth!!.id != null)
                return false
        } else if (id != oth!!.id)
            return false
        if (name == null) {
            if (oth.name != null)
                return false
        } else if (name != oth.name)
            return false
        return true
    }

    override fun toString(): String {
        return "Room [id=" + id + ", name=" + name +
                ", price=" + price + ", capacity=" + capacity +
                ", description=" + description + ", bath=" + bath +
                ", bed=" + bed + ", internet=" + internet + ", type=" + type +
                ", status=" + status + "]"
    }

    override fun hashCode(): Int {
        var result = (id ?: 0).toInt()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (price ?: 0)
        result = 31 * result + (capacity ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (bath?.hashCode() ?: 0)
        result = 31 * result + (bed ?: 0)
        result = 31 * result + (internet?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (booking?.hashCode() ?: 0)
        result = 31 * result + images.hashCode()
        return result
    }

}