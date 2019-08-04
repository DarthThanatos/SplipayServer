package controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import splitpay.Application
import splitpay.repository.InvolvedPayeesRepository
import util.Monter
import util.ListFetcher


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class, ListFetcher::class, Monter::class), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class InvolvedTest{

    @Autowired private lateinit var restTemplate: TestRestTemplate
    @Autowired private lateinit var involvedPayeesRepository: InvolvedPayeesRepository
    @Autowired private lateinit var listFetcher: ListFetcher
    @Autowired private lateinit var monter: Monter

    @Test
    fun testGetInvolved(){
        val url = "http://localhost:8080/involved/2"
        val involved = listFetcher.getBills(url)
        val bill = monter.getMockedBill(1, 20, "pizza")
        assert(bill in involved)
    }


    @Test
    fun testPostDelete(){
        val url = "http://localhost:8080/involved"

        val involved = listOf(monter.getMockedInvolved(-1, 3), monter.getMockedInvolved(-1, 1))
        val res = listFetcher.postInvolved(url, involved)//restTemplate.postForObject(url, involved, Bills::class.java)
        assert(res.all { involvedPayeesRepository.findById(it.id).isPresent })

        res.forEach{
            val deleteUrl = "http://localhost:8080/involved/${it.id}"
            restTemplate.delete(deleteUrl)
        }
        assert(res.all { !involvedPayeesRepository.findById(it.id).isPresent })
    }
}