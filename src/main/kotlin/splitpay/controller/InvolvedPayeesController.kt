package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import splitpay.model.InvolvedBillPayees
import splitpay.service.InvolvedPayeesService

@Suppress("unused")
@RestController
class InvolvedPayeesController{

    @Autowired private lateinit var involvedPayeesService: InvolvedPayeesService

    @GetMapping("/involved/{memberid}")
    fun involvedBills(@PathVariable("memberid") memberId: Long) =
        involvedPayeesService.findInvolvedBills(memberId)


    @PostMapping("/involved")
    fun addInvolved(@RequestBody involved: List<InvolvedBillPayees>){
        involvedPayeesService.addInvolved(involved)
    }

}