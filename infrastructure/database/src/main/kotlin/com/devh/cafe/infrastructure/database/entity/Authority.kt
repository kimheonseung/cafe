package com.devh.cafe.infrastructure.database.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "authority")
class Authority(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 30, nullable = false, unique = true)
    var name: String,
    @Column(length = 30) @ColumnDefault("NULL")
    var description: String? = null,

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL])
    val members: ArrayList<Member> = arrayListOf()
) {

    override fun equals(other: Any?): Boolean {
        return if (other is Authority) {
            this.id != null && other.id != null && this.id == other.id
        } else false
    }

}