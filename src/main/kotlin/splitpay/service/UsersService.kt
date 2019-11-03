package splitpay.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import splitpay.model.User
import splitpay.repository.UserRepository

@Service
class UsersService{

    @Autowired private lateinit var usersRepository: UserRepository

    fun getUser(id: Long) = usersRepository.findById(id)

    fun getUsersInGroup(groupId: Long): List<User> =
        usersRepository.getUsersInGroup(groupId)

    fun addUser(user: User) = usersRepository.save(User.withDefaultsSupplied(user))

    fun deleteUser(userId: Long) {
        usersRepository.deleteById(userId)
    }

}