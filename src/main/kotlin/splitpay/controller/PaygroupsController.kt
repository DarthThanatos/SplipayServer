package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import splitpay.model.Paygroups
import splitpay.repository.PaygroupRepository
import splitpay.service.PaygroupsService

@Suppress("unused")
@RestController
class PaygroupsController{

    @Autowired private lateinit var paygroupsService: PaygroupsService

    @GetMapping("/paygroups/paygroup/{paygroupid}")
    fun getPaygroup(@PathVariable("paygroupid") id: Long)
            = paygroupsService.getPaygroup(id)

    //e.g. localhost:8080/paygroups/user/20005
    @GetMapping("/paygroups/user/{userid}")
    fun getPaygroupsOfUser(@PathVariable("userid") userId: Long) =
        paygroupsService.getGroupsOfUser(userId)

    //e.g. localhost:8080/paygroups/group-name/20005/coco
    @GetMapping("/paygroups/group-name/{userId}/{groupName}")
    fun getGroupsOfUserWithGroupName(
        @PathVariable("userId") userId: Int,
        @PathVariable("groupName") groupName: String
    ) =
        paygroupsService.getGroupsOfUserWithGroupDisplayName(userId, groupName)


    @PostMapping("/paygroups")
    fun addPaygroup(@RequestBody paygroup: Paygroups) =
        paygroupsService.addPaygroup(paygroup)

    @DeleteMapping("/paygroups/{groupid}")
    fun delete(@PathVariable("groupid") groupid: Long){
        paygroupsService.delete(groupid)
    }
}