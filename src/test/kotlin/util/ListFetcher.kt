package util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import splitpay.model.Bills
import splitpay.model.Involvedbillpayees
import splitpay.model.Paygroups

@Component
class ListFetcher{

    @Autowired private lateinit var restTemplate: TestRestTemplate

    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<List<T>>() {
        init{T::class.java.apply{}}
    }

    private inline fun <reified T> getList(url: String, method: HttpMethod = HttpMethod.GET, requestEntity: HttpEntity<List<T>>? = null) =
        restTemplate.exchange(url, method, requestEntity, typeReference<T>()).body!!

    fun getPaygroups(url: String) = getList<Paygroups>(url)
    fun getBills(url: String) = getList<Bills>(url)
    fun postInvolved(url: String, involved: List<Involvedbillpayees>): List<Involvedbillpayees> {
        val requestEntity = HttpEntity(involved)
        return getList(url, HttpMethod.POST, requestEntity)
    }
}