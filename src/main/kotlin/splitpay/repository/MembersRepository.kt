package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import splitpay.model.Members

@Suppress("unused")
@Repository
interface MembersRepository: JpaRepository<Members, Long>{

    fun findByUserUserid(userId: Long): List<Members>

}