package com.hms.repository

import com.hms.model.RoomImages
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

@Transactional
interface RoomImageRepository : JpaRepository<RoomImages, Int> {

    @Query("SELECT e.* FROM ROOM_IMAGE AS e WHERE e.room = :room", nativeQuery = true)
    fun findByRoomId(@Param("room") room: Int): List<RoomImages>
}