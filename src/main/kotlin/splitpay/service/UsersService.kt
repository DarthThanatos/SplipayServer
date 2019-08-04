package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.Users
import splitpay.repository.UserRepository

@Service
class UsersService{

    @Autowired private lateinit var usersRepository: UserRepository

    fun getUser(id: Long) = usersRepository.findById(id)

    fun getUsersInGroup(groupId: Long): List<Users> =
        usersRepository.getUsersInGroup(groupId)

    fun addUser(user: Users) = usersRepository.save(user)

}