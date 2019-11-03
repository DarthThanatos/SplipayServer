package splitpay.repository

import splitpay.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface TestStoredProcedure{
    fun helloTest(): String
    fun getSesoco(): List<User>
    fun getSesocoIds(): List<Int>
}

@Repository
interface UserRepository: JpaRepository<User, Long>, TestStoredProcedure{

    @Query("select u from User u where displayname like %?1")
    fun findWithSpecifiedName(name: String): List<User>

    @Query("select * from users u where displayname like %?1", nativeQuery = true)
    fun findWithSpecifiedNameNative(name: String): List<User>

    @Query("select u from User u join Members m on m.user.userid = u.userid where groupid = ?1")
    fun getUsersInGroup(groupId: Long): List<User>
}

@Suppress("unused", "UNCHECKED_CAST")
class TestStoredProcedureImpl: TestStoredProcedure{

    @PersistenceContext
    lateinit var em: EntityManager

    override fun helloTest() =
        em.createNativeQuery("select hello()").singleResult.toString()

    override fun getSesoco(): List<User> =
        em.createNativeQuery("select * from getSesoco()", User::class.java).resultList as List<User>

    override fun getSesocoIds(): List<Int> =
        em.createNativeQuery("select * from getSesocoIds()").resultList. map {it as Int}
}