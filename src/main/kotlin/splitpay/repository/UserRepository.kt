package splitpay.repository

import splitpay.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface TestStoredProcedure{
    fun helloTest(): String
    fun getSesoco(): List<Users>
    fun getSesocoIds(): List<Int>
}

@Repository
interface UserRepository: JpaRepository<Users, Long>, TestStoredProcedure{

    @Query("select u from Users u where displayname like %?1")
    fun findWithSpecifiedName(name: String): List<Users>

    @Query("select * from users u where displayname like %?1", nativeQuery = true)
    fun findWithSpecifiedNameNative(name: String): List<Users>

    @Query("select u from Users u join Members m on m.user.userid = u.userid where groupid = ?1")
    fun getUsersInGroup(groupId: Long): List<Users>
}

@Suppress("unused", "UNCHECKED_CAST")
class TestStoredProcedureImpl: TestStoredProcedure{

    @PersistenceContext
    lateinit var em: EntityManager

    override fun helloTest() =
        em.createNativeQuery("select hello()").singleResult.toString()

    override fun getSesoco(): List<Users> =
        em.createNativeQuery("select * from getSesoco()", Users::class.java).resultList as List<Users>

    override fun getSesocoIds(): List<Int> =
        em.createNativeQuery("select * from getSesocoIds()").resultList. map {it as Int}
}