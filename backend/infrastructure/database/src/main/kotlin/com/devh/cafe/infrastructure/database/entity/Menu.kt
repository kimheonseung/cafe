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
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "menu")
class Menu(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 50, nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var price: Long,

    @Column(nullable = false)
    var available: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true, mappedBy = "menu")
    val options: MutableList<Option> = mutableListOf(),
) {
    init {
        this.category.addMenu(this)
    }

    fun changeName(name: String) {
        this.name = name
    }

    fun changePrice(price: Long) {
        this.price = price
    }

    fun adjustDiscount(discountRate: Int) {
        this.price *= (1 - (discountRate/100))
    }

    fun changeToSoldOut() {
        this.available = false
    }

    fun changeToOnSale() {
        this.available = true
    }

    fun changeAvailable(available: Boolean) {
        this.available = available
    }

    fun changeCategory(category: Category) {
        this.category.removeMenu(this)
        this.category = category
        category.addMenu(this)
    }

    fun addOption(option: Option) {
        this.options.add(option)
    }

    fun removeOption(option: Option) {
        this.options.remove(option)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Menu) {
            other.name == this.name
        } else false
    }

    override fun toString(): String {
        return "Menu[" +
                    "id: ${this.id}, " +
                    "category: ${this.category.name}, " +
                    "name: ${this.name}, " +
                    "price: ${this.price}, " +
                    "available: ${this.available}, " +
                    "options: ${this.options}" +
                "]"
    }
}
