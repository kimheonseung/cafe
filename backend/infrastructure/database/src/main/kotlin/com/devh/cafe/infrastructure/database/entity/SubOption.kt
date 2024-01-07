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
@Table(name = "sub_option")
class SubOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = false)
    val price: Long,

    @Column(nullable = false)
    val displayOrder: Int,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "option_id")
    val option: Option,
) {

    init {
        option.addSubOption(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is SubOption) {
            other.name == this.name
        } else false
    }

    override fun toString(): String {
        return "SubOption[" +
                    "id: ${this.id}, " +
                    "name: ${this.name}, " +
                    "price: ${this.price}, " +
                    "displayOrder: ${this.displayOrder}, " +
                    "option: ${this.option.name}, " +
                "]"
    }
}
