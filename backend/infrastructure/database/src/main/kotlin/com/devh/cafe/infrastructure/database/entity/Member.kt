package com.devh.cafe.infrastructure.database.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "authority_id")
    var authority: Authority? = null
) : BaseEntity() {

    fun updateAuthority(authority: Authority) {
        removeAuthority()
        this.authority = authority
        authority.members.add(this)
    }

    fun removeAuthority() {
        if (this.authority != null) {
            this.authority!!.members.remove(this)
        }
        this.authority = null
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Member) {
            this.id != null && other.id != null && this.id == other.id
        } else false
    }

}