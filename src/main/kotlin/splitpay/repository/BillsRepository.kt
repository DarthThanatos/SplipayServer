package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import splitpay.model.Bills

@Repository
interface BillsRepository: JpaRepository<Bills, Long>{

    fun findByPayerMemberid(memberId: Long): List<Bills>

}