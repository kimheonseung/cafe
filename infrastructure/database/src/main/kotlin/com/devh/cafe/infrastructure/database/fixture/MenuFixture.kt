package com.devh.cafe.infrastructure.database.fixture

import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.entity.Menu

val fixtureMenuAmericano = Menu(id = 2001, name = "아메리카노", price = 1500, available = true, category = fixtureCategoryCaffeine)
val fixtureMenuLatte = Menu(id = 2002, name = "라떼", price = 2500, available = true, category = fixtureCategoryCaffeine)
val fixtureMenuLemonAid = Menu(id = 2003, name = "레몬에이드", price = 2500, available = true, category = fixtureCategoryFlatccino)
val fixtureMenuMintChoco = Menu(id = 2004, name = "민트초코", price = 3000, available = true, category = fixtureCategoryFlatccino)
val fixtureMenuSaltBread = Menu(id = 2005, name = "소금빵", price = 2000, available = true, category = fixtureCategoryBakery)

fun fixtureMenu(category: Category): MutableList<Menu> {
    return when (category.name) {
        fixtureCategoryCaffeine.name -> mutableListOf(fixtureMenuAmericano, fixtureMenuLatte)
        fixtureCategoryFlatccino.name -> mutableListOf(fixtureMenuLemonAid, fixtureMenuMintChoco)
        fixtureCategoryBakery.name -> mutableListOf(fixtureMenuSaltBread)
        else -> {
            mutableListOf()
        }
    }
}

fun newMenu(category: Category, price: Long): Menu {
    return Menu(name = "menu-${Math.random()}", category = category, price = price)
}
