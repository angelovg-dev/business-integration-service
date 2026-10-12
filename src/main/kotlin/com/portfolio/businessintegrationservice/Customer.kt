package com.portfolio.businessintegrationservice

import jakarta.persistence.*

@Entity
@Table(name = "customers")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var firstName: String = ""

    @Column(nullable = false)
    var lastName: String = ""

    @Column(nullable = false)
    var email: String = ""

    var predictedAge: Int? = null
}