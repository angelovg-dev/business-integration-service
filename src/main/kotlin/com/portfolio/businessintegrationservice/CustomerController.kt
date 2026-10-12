package com.portfolio.businessintegrationservice

import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerRepository: CustomerRepository
) {
    @GetMapping
    fun list(): List<Customer> {
        return customerRepository.findAll()
    }
    @PostMapping
    fun create(@RequestBody customer: Customer): Customer {
        return customerRepository.save(customer)
    }
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Customer {
        return customerRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        }
    }
    @PostMapping("/{id}/enrich")
    fun enrich(@PathVariable id: Long): Customer {
        val customer = customerRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        }
        val restTemplate = RestTemplate()
        val url = "https://api.agify.io?name=${customer.firstName}"
        val agify = restTemplate.getForObject(url, AgifyResponse::class.java)
        customer.predictedAge = agify?.age
        return customerRepository.save(customer)
    }
}

data class AgifyResponse(
    val name: String? = null,
    val age: Int? = null,
    val count: Int? = null
)