package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Members
import splitpay.repository.MembersRepository

@Service
class MembersService {

    @Autowired private lateinit var membersRepository: MembersRepository

    fun getMembersByUserid(userId: Long) = membersRepository.findByUserUserid(userId)

    fun getBalancesOfUser(userId: Long): List<Int>{
        val membersList = membersRepository.findByUserUserid(userId)
        return membersList.map { it.balance }
    }

    fun addMember(member: Members) = membersRepository.save(member)

}