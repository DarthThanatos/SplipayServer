package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import splitpay.model.Users
import splitpay.service.UsersService

@Suppress("unused")
@RestController
class UsersController{

    @Autowired private lateinit var usersService: UsersService

    //e.g. localhost:8080/users/20005
    @GetMapping("/users/{userid}")
    fun getUser(@PathVariable(name = "userid") userId: Long)
            = usersService.getUser(userId)

    //e.g. localhost:8080/users/groups/3
    @GetMapping("/users/groups/{groupid}")
    fun getUsersInGroup(@PathVariable(name = "groupid") groupId: Long) = usersService.getUsersInGroup(groupId)


    @PostMapping("/users")
    fun addUser(@RequestBody user: Users) =
        usersService.addUser(user)


}