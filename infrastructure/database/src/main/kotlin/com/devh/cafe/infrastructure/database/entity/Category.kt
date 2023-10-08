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
@Table(name = "category")
class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 50, nullable = false, unique = true)
    var name: String,

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val menu: MutableList<Menu> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val subCategories: MutableList<Category> = mutableListOf()
) {
    fun changeName(name: String) {
        this.name = name
    }

    fun addMenu(menu: Menu) {
        this.menu.add(menu)
    }

    fun removeMenu(menu: Menu) {
        this.menu.remove(menu)
    }

    fun addSubCategory(child: Category) {
        this.subCategories.add(child)
        child.addParent(this)
    }

    fun removeSubCategory(child: Category) {
        this.subCategories.remove(child)
        child.removeParent()
    }

    private fun addParent(parent: Category) {
        this.parent = parent
    }

    private fun removeParent() {
        this.parent = null
    }

    fun hasParent(): Boolean {
        return this.parent != null
    }

    fun hasSubCategories(): Boolean {
        return this.subCategories.size > 0
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Category) {
            this.name == other.name
        } else false
    }

    override fun toString(): String {
        return "Category[id: ${this.id}, name: ${this.name}, parent: ${this.parent?.name}, subCategories: ${this.subCategories}]"
    }
}
