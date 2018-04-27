package com.hms.repository

import com.hms.model.RoomType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface RoomTypeRepository : JpaRepository<RoomType, Int> {
    fun findByType(type: String): RoomType
}