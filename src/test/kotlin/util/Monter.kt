package util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import splitpay.model.*
import splitpay.repository.BillsRepository
import splitpay.repository.MembersRepository
import splitpay.repository.PaygroupRepository

@Component
class Monter{

    @Autowired private lateinit var billsRepository: BillsRepository
    @Autowired private lateinit var paygroupRepository: PaygroupRepository
    @Autowired private lateinit var membersRepository: MembersRepository

    fun getMockedBill(id: Long = -1, amount: Long = 0, displayName: String = ""): Bills{
        val bill = Bills(id, amount, displayName)
        val billId = if(id == -1L) 1 else id
        val fetchedBill = billsRepository.findById(billId).get()
        bill.payer = fetchedBill.payer
        bill.payer.user = fetchedBill.payer.user
        bill.payer.paygroup = fetchedBill.payer.paygroup
        bill.payer.paygroup.leader = fetchedBill.payer.paygroup.leader
        return bill
    }

    fun getMockedGroup(id: Long = -1, displayName: String = "", isActive: Boolean = false): Paygroups{
        val paygroups = Paygroups(id, displayName, isActive)
        val groupId = if(id == -1L) 1 else id
        paygroups.leader = paygroupRepository.findById(groupId).get().leader
        return paygroups

    }

    fun getMockedMember(id: Long = -1, balance: Int = 0): Members{
        val member = Members(id, balance)
        val memberId = if(id == -1L) 2 else id
        val fetchedMember = membersRepository.findById(memberId).get()
        member.paygroup = fetchedMember.paygroup
        member.user = fetchedMember.user
        return member
    }

    fun getMockedInvolved(id: Long = -1, memberId: Long = 2): Involvedbillpayees{
        val involved = Involvedbillpayees(id)
        involved.bill = getMockedBill(1)
        involved.member = getMockedMember(memberId)
        return involved
    }
}