package com.hms.repository

import com.hms.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
interface RoomRepository : JpaRepository<Room, Int> {

    fun findByName(name: String): Optional<Room>

    @Query("SELECT m.* from Room AS m WHERE m.type = :typeId AND m.status = :status", nativeQuery = true)
    fun findByTypeIdAndStatus(typeId: Int, status: String): List<Room>

    fun findByStatus(status: String): List<Room>

    fun deleteByName(name: String)

    @Query("SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room_id = d.id WHERE (c.room_id IS NULL) ORDER BY d.name) AS t ON m.id = t.id WHERE m.status LIKE :check", nativeQuery = true)
    fun findFreeRooms(@Param("check") check: String): List<Room>

    @Query("SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room_id = d.id WHERE (c.room_id IS NULL) ORDER BY d.name) AS t ON m.id = t.id WHERE m.status LIKE :check and m.price >= :minimum and m.price <= :maximum", nativeQuery = true)
    fun findFreeRooms(@Param("minimum") min: Int, @Param("maximum") max: Int, @Param("check") check: String): List<Room>

    @Query("SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room_id = d.id WHERE (c.room_id IS NULL) ORDER BY d.name) AS t ON m.id = t.id WHERE m.status LIKE :check and m.price >= :minimum and m.price <= :maximum and m.type = :type", nativeQuery = true)
    fun findFreeRooms(@Param("minimum") min: Int, @Param("maximum") max: Int, @Param("type") type: Int, @Param("check") check: String): List<Room>

    @Query("SELECT m.* FROM Room AS m JOIN (SELECT e.* FROM Room AS e WHERE e.name LIKE :name) AS t ON m.id = t.id WHERE m.status LIKE :verify", nativeQuery = true)
    fun findByName(@Param("name") name: String, @Param("verify") verify: String): List<Room>
}