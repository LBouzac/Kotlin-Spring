package com.example.tchat.TPCRUD


import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    //http://localhost:8080/users
    @GetMapping
    fun getAllUsers() =  ResponseEntity.ok(UserService.load())

    //http://localhost:8080/users/1
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long?): ResponseEntity<UserBean> {
        val userBean = UserService.findById(id)
        return if (userBean != null) {
            ResponseEntity.ok(userBean)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users
    //{"login":"aaa", "password":"bbb"}
    @PostMapping
    fun createUser(@Valid @RequestBody user: UserBean?) = ResponseEntity(user?.let { UserService.save(it) }, HttpStatus.CREATED)

    //http://localhost:8080/users/1
    //{"login":"aaa", "password":"bbb"}
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long?,
        @Valid @RequestBody userDetails: UserBean?
    ): ResponseEntity<UserBean> {
        val user = UserService.findById(id)
        return if (user != null) {
            if (userDetails != null) {
                userDetails.id = id
            } //écrase celui reçu dans le JSON au cas ou
            if (userDetails != null) {
                UserService.save(userDetails)
            }
            ResponseEntity(userDetails, HttpStatus.OK)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users/1
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long?): ResponseEntity<Void> {
        return if (id?.let { UserService.deleteById(it) } == true) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}