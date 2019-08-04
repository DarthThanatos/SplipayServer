package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Involvedbillpayees
import splitpay.repository.InvolvedPayeesRepository

@Service
class InvolvedPayeesService {

    @Autowired private lateinit var involvedPayeesRepository: InvolvedPayeesRepository

    fun findInvolvedBills(memberId: Long) =
        involvedPayeesRepository.findByMemberMemberid(memberId).map { it.bill }

    fun addInvolved(involved: List<Involvedbillpayees>): List<Involvedbillpayees> {
        val res = mutableListOf<Involvedbillpayees>()
        involved.forEach { res.add(involvedPayeesRepository.save(it)) }
        return res
    }

    fun delete(id: Long) {
        involvedPayeesRepository.delete(Involvedbillpayees(id))
    }

}