package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Paygroups
import splitpay.repository.PaygroupRepository

@Service
class PaygroupsService{

    @Autowired private lateinit var paygroupRepository: PaygroupRepository

    fun getPaygroup(groupId: Long) = paygroupRepository.findById(groupId)

    fun getAllPaygroups() = paygroupRepository.getAll()

    fun getAllOfParticipating(userId: Long) = paygroupRepository.getAllOfParticipating()

    fun getGroupsOfUser(userId: Long)  = paygroupRepository.getAllOfUser()//paygroupRepository.findByUserid(userId)

    fun getGroupsOfUserWithGroupDisplayName(userId: Int, groupName: String)
        = paygroupRepository.findGroupByUserAndGroupName(userId, groupName)

    fun addPaygroup(paygroup: Paygroups) = paygroupRepository.save(paygroup)

    fun delete(groupid: Long){
        paygroupRepository.delete(Paygroups(groupid))
    }
}