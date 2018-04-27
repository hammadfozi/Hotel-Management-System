package com.hms.repository

import com.hms.model.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface UserProfileRepository : JpaRepository<UserProfile, Int> {

    fun findByType(type: String): UserProfile
}