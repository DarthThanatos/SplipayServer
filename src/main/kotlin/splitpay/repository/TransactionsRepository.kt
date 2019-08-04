package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import splitpay.model.Transactions

@Suppress("unused")
@Repository
interface TransactionsRepository: JpaRepository<Transactions, Long>{

    fun findByFrommemberMemberid(memberId: Long): List<Transactions>

    fun findByFrommemberMemberidAndTomemberMemberid(fromMember: Long, toMember: Long): List<Transactions>

}