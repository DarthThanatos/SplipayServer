package splitpay.model

import splitpay.util.DEFAULT_AVATAR_URL
import javax.persistence.*

@Entity()
@Table(name="users")
data class User(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) var userid: Long? = null,
    val email: String? = null,
    var displayname: String? = null,
    var isoffline: Boolean? = null,
    var avatarUrl: String? = null
){
    companion object {
        fun withDefaultsSupplied(user: User): User{
            val res = user.apply {
                avatarUrl = if((avatarUrl ?: "").trim() == "") DEFAULT_AVATAR_URL else avatarUrl
                isoffline = if(isoffline == null) true else isoffline
            }
            println("User with filled defaults: $user")
            return res
        }
    }
}

@Entity
data class Paygroups(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val groupid: Long,
    val displayname: String = "",
    val isactive: Boolean = false
){

      @ManyToOne @JoinColumn(name = "leaderuserid") lateinit var leader: User
}

@Entity
data class Members(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val memberid: Long,
    val balance: Int = 0
){
    @ManyToOne @JoinColumn(name = "userid") lateinit var user: User
    @ManyToOne @JoinColumn(name = "groupid") lateinit var paygroup: Paygroups
}


@Entity
data class Bills (
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val billid: Long,
    val amount: Long = 0,
    val displayname: String = ""
){
    @ManyToOne @JoinColumn(name = "payer") lateinit var payer: Members
}


@Entity
data class Involvedbillpayees(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val id: Long
){
    @ManyToOne @JoinColumn(name = "billid") lateinit var bill: Bills
    @ManyToOne @JoinColumn(name = "memberid") lateinit var member: Members
}


@Entity
data class Transactions(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val transactionid: Long,
    val amount: Long
){
    @ManyToOne @JoinColumn(name = "frommember") lateinit var frommember: Members
    @ManyToOne @JoinColumn(name = "tomember") lateinit var tomember: Members
}
