package com.hms.model

import org.hibernate.annotations.Proxy
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "FEEDBACK")
@Proxy(lazy = false)
class Feedback : Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "NAME", nullable = false)
    var name: String? = null

    @Column(name = "EMAIL", nullable = false)
    var email: String? = null

    @Column(name = "COMMENT", nullable = true)
    var comment: String? = null

    @Column(name = "RATING", nullable = true)
    var rating: Int? = null
}