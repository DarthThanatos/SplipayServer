package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import splitpay.service.MembersService

@Suppress("unused")
@RestController
class MembersController{

    @Autowired private lateinit var membersService: MembersService

    // e.g. localhost:8080/user/members/20005
    @GetMapping("/user/members/{userid}")
    fun getMembersOfUser(@PathVariable(name = "userid") userId: Long)
        = membersService.getMembersByUserid(userId)

    // e.g. localhost:8080/user/balances/20005
    @GetMapping("/user/balances/{userid}")
    fun getBalancesOfUser(@PathVariable(name = "userid") userId: Long)
        = membersService.getBalancesOfUser(userId)

}