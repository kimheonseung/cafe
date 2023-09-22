package com.devh.cafe.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 20, nullable = false, unique = true)
    val username: String,
    @Column(nullable = false)
    var password: String,
    @Column(length = 50, nullable = false)
    val name: String,
    @Column(length = 50, nullable = false)
    var nickname: String,
    @Column(nullable = false, unique = true)
    val email: String,
) : BaseEntity()