package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Bills
import splitpay.repository.BillsRepository

@Service
class BillsService{

    @Autowired private lateinit var billsRepository: BillsRepository

    fun getBillsOfMember(memberId: Long)
            = billsRepository.findByPayerMemberid(memberId)

    fun addBill(bill: Bills) = billsRepository.save(bill)

    fun delete(billdId: Long) {
        billsRepository.delete(Bills(billdId))
    }
}