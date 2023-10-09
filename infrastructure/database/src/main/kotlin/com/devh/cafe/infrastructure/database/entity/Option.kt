package com.devh.cafe.infrastructure.database.entity

import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "option")
class Option(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = false)
    val displayOrder: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: OptionType,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "menu_id")
    val menu: Menu? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "category_id")
    val category: Category? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true, mappedBy = "option")
    val subOptions: MutableList<SubOption> = mutableListOf(),
) {

    init {
        menu?.addOption(this)
        category?.addOption(this)
    }

    fun addSubOption(subOption: SubOption) {
        this.subOptions.add(subOption)
    }

    fun removeSubOption(subOption: SubOption) {
        this.subOptions.remove(subOption)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Option) {
            other.name == this.name
        } else false
    }

    override fun toString(): String {
        return "Option[" +
                    "id: ${this.id}, " +
                    "name: ${this.name}, " +
                    "displayOrder: ${this.displayOrder}, " +
                    "type: ${this.type}, " +
                    "menu: ${this.menu?.name}, " +
                    "category: ${this.category?.name}, " +
                    "subOptions: ${this.subOptions}, " +
                "]"
    }
}
