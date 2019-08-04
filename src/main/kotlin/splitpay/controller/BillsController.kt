package splitpay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import splitpay.model.Bills
import splitpay.service.BillsService

@RestController
@Suppress("unused")
class BillsController{

    @Autowired private lateinit var billsService: BillsService

    //e.g. localhost:8080/member/bills/21
    @GetMapping("/member/bills/{memberid}")
    fun getMemberBills(@PathVariable("memberid") memberId: Long)
            = billsService.getBillsOfMember(memberId)

    @PostMapping("/member/bills")
    fun postBill(@RequestBody bill: Bills) =
        billsService.addBill(bill)


    @DeleteMapping("/member/bills/{billid}")
    fun deleteBill(@PathVariable("billid") billdId: Long){
        billsService.delete(billdId)
    }
}