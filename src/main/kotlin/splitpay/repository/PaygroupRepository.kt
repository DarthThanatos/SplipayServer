package splitpay.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import splitpay.model.Paygroups
import splitpay.model.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface PaygroupsStoredFunctions{
    fun findGroupByUserAndGroupName(userId: Int, groupName: String): List<Paygroups>
}

@Repository
interface PaygroupRepository: JpaRepository<Paygroups, Long>, PaygroupsStoredFunctions{

    fun findByLeader(leader: User): Paygroups?
    fun findByLeaderUserid(leaderid: Long): Paygroups?

    @Query("select p from Paygroups p join Members m on m.paygroup.groupid = p.groupid where userid = ?1")
    fun findByUserid(userid: Long): List<Paygroups>

    @Query(value="SELECT * FROM paygroups LIMIT 10", nativeQuery = true)
    fun getAll(): List<Paygroups>

    @Query(value="SELECT * FROM paygroups LIMIT 5", nativeQuery = true)
    fun getAllOfUser(): List<Paygroups>

    @Query(value="SELECT * FROM paygroups LIMIT 3", nativeQuery = true)
    fun getAllOfParticipating(): List<Paygroups>
}

@Suppress("UNCHECKED_CAST", "unused")
class PaygroupsStoredFunctionsImpl: PaygroupsStoredFunctions{

    @PersistenceContext private lateinit var em: EntityManager

    override fun findGroupByUserAndGroupName(userId: Int, groupName: String) =
        em.createNativeQuery("select * from groupsOfUserLike(?1, ?2)", Paygroups::class.java)
            .setParameter(1, userId)
            .setParameter(2, groupName)
            .resultList as List <Paygroups>

}
