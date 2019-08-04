package controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import splitpay.Application
import splitpay.model.Bills
import splitpay.repository.BillsRepository
import util.Monter
import util.ListFetcher

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class, ListFetcher::class, Monter::class), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BillsControllerTest{

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var billsRepository: BillsRepository

    @Autowired private lateinit var listFetcher: ListFetcher

    @Autowired private lateinit var monter: Monter

    @Test
    fun testGetBills(){
        val url = "http://localhost:8080/member/bills/21"
        val bills = listFetcher.getBills(url)
        assert(Bills(1, 20, "pizza") in bills)
    }

    @Test
    fun testPostDelete(){
        val url = "http://localhost:8080/member/bills"
        val bill = monter.getMockedBill(amount = 330, displayName = "testx")
        val res = restTemplate.postForObject(url, bill, Bills::class.java)
        assert (res.amount == bill.amount)

        val deleteUrl = "http://localhost:8080/member/bills/${res.billid}"
        assert(billsRepository.findById(res.billid).isPresent)
        restTemplate.delete(deleteUrl)
        assert(!billsRepository.findById(res.billid).isPresent)
    }

}