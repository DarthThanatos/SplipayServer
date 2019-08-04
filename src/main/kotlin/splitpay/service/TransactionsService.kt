package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Transactions
import splitpay.repository.TransactionsRepository

@Service
class TransactionsService{

    @Autowired private lateinit var transactionsRepository: TransactionsRepository

    fun addTransaction(transactions: Transactions) = transactionsRepository.save(transactions)

    fun getAllMyTransactions(memberId: Long) = transactionsRepository.findByFrommemberMemberid(memberId)

    fun getAllMyTransactionsTo(memberId: Long, receiver: Long) =
        transactionsRepository.findByFrommemberMemberidAndTomemberMemberid(memberId, receiver)
}