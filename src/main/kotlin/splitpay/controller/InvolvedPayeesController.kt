package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import splitpay.model.Involvedbillpayees
import splitpay.service.InvolvedPayeesService

@Suppress("unused")
@RestController
class InvolvedPayeesController{

    @Autowired private lateinit var involvedPayeesService: InvolvedPayeesService

    // e.g. http://localhost:8080/involved/2
    @GetMapping("/involved/{memberid}")
    fun involvedBills(@PathVariable("memberid") memberId: Long) =
        involvedPayeesService.findInvolvedBills(memberId)

    @PostMapping("/involved")
    fun addInvolved(@RequestBody involved: List<Involvedbillpayees>) =
        involvedPayeesService.addInvolved(involved)

    @DeleteMapping("/involved/{id}")
    fun delete(@PathVariable("id") id: Long){
        involvedPayeesService.delete(id)
    }
}