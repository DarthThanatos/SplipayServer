package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.InvolvedBillPayees
import splitpay.repository.InvolvedPayeesRepository

@Service
class InvolvedPayeesService {

    @Autowired private lateinit var involvedPayeesRepository: InvolvedPayeesRepository

    fun findInvolvedBills(memberId: Long) =
        involvedPayeesRepository.findByMemberMemberid(memberId).map { it.bill }

    fun addInvolved(involved: List<InvolvedBillPayees>) =
        involved.forEach { involvedPayeesRepository.save(it) }

}