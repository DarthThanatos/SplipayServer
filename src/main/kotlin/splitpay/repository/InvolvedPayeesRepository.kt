package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import splitpay.model.Involvedbillpayees

@Suppress("unused")
@Repository
interface InvolvedPayeesRepository: JpaRepository<Involvedbillpayees, Long>{

    fun findByMemberMemberid(memberId: Long): List<Involvedbillpayees>

}