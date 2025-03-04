package com.puj.admincenter.controller

import com.puj.admincenter.domain.users.User
import com.puj.admincenter.dto.users.CreateUserDto
import com.puj.admincenter.dto.users.ModifyUserDto
import com.puj.admincenter.dto.users.UserDto
import com.puj.admincenter.service.UserService

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.validation.Valid
import javax.servlet.http.HttpServletRequest
import java.security.Principal
import java.util.Date
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CrossOrigin
@RequestMapping("/users")
@RestController
class UserController(private val userService: UserService) {
    companion object {
        val logger = LoggerFactory.getLogger(UserController::class.java)!!
    }

    
    @GetMapping(
        value = ["/{userId}"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun getById(@PathVariable userId: Int,
                @RequestHeader(value="authorization", required=true) authorization: String): ResponseEntity<*>
        = userService.getById(userId,
                              authorization)

    @PostMapping(
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun create(@RequestBody @Valid createUserDto: CreateUserDto, 
               @RequestHeader(value="authorization", required=true) authorization: String): ResponseEntity<*>
        = userService.create(createUserDto)

    @PutMapping(
        value = ["/modify"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun modify(@RequestBody @Valid modifyUserDto: ModifyUserDto, 
               @RequestHeader(value="authorization", required=true) authorization: String): ResponseEntity<*>
        = userService.modify(modifyUserDto)
}