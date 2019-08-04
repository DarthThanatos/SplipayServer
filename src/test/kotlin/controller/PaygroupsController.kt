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
import splitpay.model.Paygroups
import splitpay.model.Users
import splitpay.repository.PaygroupRepository

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PaygroupsControllerTest{

    @Autowired private lateinit var restTemplate: TestRestTemplate
    @Autowired private lateinit var paygroupRepository: PaygroupRepository

    @Test
    fun testGetGroup(){
        val res =
            restTemplate
                .getForObject(
                    "http://localhost:8080/paygroups/paygroup/3",
                    Paygroups::class.java
                )
        val expected = Paygroups(displayname = "test group 2", groupid = 3, isactive = true)
        assert(res == expected)
    }

    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<List<T>>() {
        init{T::class.java.apply{}}
    }

    private inline fun <reified T> getList(url: String) =
        restTemplate.exchange(url, HttpMethod.GET, null, typeReference<T>()).body!!

    @Test
    fun testGetUserGroups(){
        val url = "http://localhost:8080/paygroups/user/20005"
        val res = getList<Paygroups>(url)
        assert(res.size == 2)
        assert(Paygroups(3, "test group 2", true) in res)
        assert(Paygroups(209, "COCOGA", true) in res)
    }

    @Test
    fun testNamedGroup(){
        val url = "http://localhost:8080/paygroups/group-name/20005/coco"
        val res = getList<Paygroups>(url)
        assert(Paygroups(209, "COCOGA", true) in res)
    }

    @Test
    fun testPost(){
        val url = "http://localhost:8080/paygroups"
        val paygroups = Paygroups(-1, "testx", false)
        paygroups.leader = Users(20004)
        val res = restTemplate.postForObject(url, paygroups, Paygroups::class.java)
        assert (res.displayname == paygroups.displayname && res.isactive == paygroups.isactive)

        val deleteUrl = "http://localhost:8080/paygroups/${res.groupid}"
        assert(paygroupRepository.findById(res.groupid).isPresent)
        restTemplate.delete(deleteUrl)
        assert(!paygroupRepository.findById(res.groupid).isPresent)
    }

}
