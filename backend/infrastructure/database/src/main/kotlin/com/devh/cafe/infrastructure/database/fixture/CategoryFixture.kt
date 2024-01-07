package com.devh.cafe.infrastructure.database.fixture

import com.devh.cafe.infrastructure.database.entity.Category

val fixtureCategoryAlcohol = Category(id = 1000, name = "알콜", parent = null)
val fixtureCategoryBeverage = Category(id = 1001, name = "음료", parent = null)
val fixtureCategoryBakery = Category(id = 1002, name = "베이커리", parent = null)
val fixtureCategoryCoffee = Category(id = 1003, name = "커피", parent = fixtureCategoryBeverage)
val fixtureCategoryFlatccino = Category(id = 1004, name = "플랫치노", parent = fixtureCategoryBeverage)
val fixtureCategoryCaffeine = Category(id = 1005, name = "카페인", parent = fixtureCategoryCoffee)
val fixtureCategoryDecaffeine = Category(id = 1006, name = "디카페인", parent = fixtureCategoryCoffee)

val fixtureCategoryBeverageSubCategories = mutableListOf(fixtureCategoryCoffee,
                                                         fixtureCategoryFlatccino)
val fixtureCategoryBeverageRecursiveSubCategories = mutableListOf(fixtureCategoryBeverage,
                                                                  fixtureCategoryCoffee,
                                                                  fixtureCategoryFlatccino,
                                                                  fixtureCategoryCaffeine,
                                                                  fixtureCategoryDecaffeine)
val fixtureCategoryCaffeineRecursiveParentCategories = mutableListOf(fixtureCategoryCaffeine,
                                                                     fixtureCategoryCoffee,
                                                                     fixtureCategoryBeverage)
val fixtureCategoryAll = mutableListOf(fixtureCategoryAlcohol,
                                       fixtureCategoryBeverage,
                                       fixtureCategoryBakery,
                                       fixtureCategoryCoffee,
                                       fixtureCategoryFlatccino,
                                       fixtureCategoryCaffeine,
                                       fixtureCategoryDecaffeine)

fun newCategory(): Category {
    return Category(name = "category-${Math.random()}")
}
