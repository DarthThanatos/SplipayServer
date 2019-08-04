package controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner
import splitpay.Application
import splitpay.model.Bills
import splitpay.model.Members
import splitpay.model.Paygroups
import splitpay.model.Users
import splitpay.repository.BillsRepository

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BillsControllerTest{

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var billsRepository: BillsRepository

    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<List<T>>() {
        init{T::class.java.apply{}}
    }

    private inline fun <reified T> getList(url: String) =
        restTemplate.exchange(url, HttpMethod.GET, null, typeReference<T>()).body!!

    @Test
    fun testGetBills(){
        val url = "http://localhost:8080/member/bills/21"
        val bills = getList<Bills>(url)
        assert(Bills(1, 20, "pizza") in bills)
    }

    @Test
    fun testPostDelete(){
        val url = "http://localhost:8080/member/bills"
        val bill = Bills(-1, 330, "testx")
        bill.payer = Members(21)
        bill.payer.user = Users(20700)
        bill.payer.paygroup = Paygroups(1)
        bill.payer.paygroup.leader = Users(20007)

        val res = restTemplate.postForObject(url, bill, Bills::class.java)
        assert (res.amount == bill.amount)

        val deleteUrl = "http://localhost:8080/member/bills/${res.billid}"
        assert(billsRepository.findById(res.billid).isPresent)
        restTemplate.delete(deleteUrl)
        assert(!billsRepository.findById(res.billid).isPresent)

    }

}