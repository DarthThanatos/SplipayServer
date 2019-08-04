package splitpay.model

import javax.persistence.*

@Entity
data class Users(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) val userid: Long,
    val email: String  = "",
    val displayname: String = "",
    val isoffline: Boolean = true,
    val avatarUrl: String = ""
)

@Entity
data class Paygroups(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val groupid: Long,
    val displayname: String = "",
    val isactive: Boolean = false
){

      @ManyToOne @JoinColumn(name = "leaderuserid") lateinit var leader: Users
}

@Entity
data class Members(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val memberid: Long,
    val balance: Int = 0
){
    @ManyToOne @JoinColumn(name = "userid") lateinit var user: Users
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
data class InvolvedBillPayees(
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
