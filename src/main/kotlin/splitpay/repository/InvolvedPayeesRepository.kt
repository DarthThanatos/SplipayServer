package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import splitpay.model.InvolvedBillPayees

@Suppress("unused")
@Repository
interface InvolvedPayeesRepository: JpaRepository<InvolvedBillPayees, Long>{

    fun findByMemberMemberid(memberId: Long): List<InvolvedBillPayees>

}