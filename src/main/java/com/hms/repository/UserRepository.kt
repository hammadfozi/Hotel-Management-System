package com.hms.repository

import com.hms.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
interface UserRepository : JpaRepository<User, Int> {

    fun findByEmail(email: String): Optional<User>

    fun findByUsername(username: String): Optional<User>

    fun deleteByUsername(username: String)

    fun deleteByEmail(email: String)

    @Query("SELECT e.user_id FROM APP_USER_USER_PROFILE AS e WHERE e.user_profile_id = :role", nativeQuery = true)
    fun findByRole(@Param("role") roleId: Int): List<Int>
}
