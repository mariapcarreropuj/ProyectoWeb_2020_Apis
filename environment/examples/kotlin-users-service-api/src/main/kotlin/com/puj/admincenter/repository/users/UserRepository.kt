package com.puj.admincenter.repository.users

import com.puj.admincenter.domain.users.User

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int>,
                           JpaSpecificationExecutor<User> {

    @Query("""
        SELECT password
        FROM User user
        WHERE user.username = :username
    """)
    fun findPasswordByUser(username: String): String?

    @Query("""
        SELECT user
        FROM User user
        WHERE user.username = :username
        AND user.password = :password
    """)
    fun findUserByUserAndPassword(username: String,
                                  password: String): User?

    @Query("""
        SELECT COUNT(user) > 0
        FROM User user
        WHERE user.email = :email
    """)
    fun existsByEmail(@Param("email") email: String): Boolean

    @Transactional
    @Modifying
    @Query("""
        UPDATE User user
        SET password = :newPassword
        WHERE user.username = :username
    """)
    fun updatePasswordByUser(username: String, newPassword: String): Int

}