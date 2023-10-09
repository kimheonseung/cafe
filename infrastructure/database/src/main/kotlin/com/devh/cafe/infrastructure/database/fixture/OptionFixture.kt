package com.devh.cafe.infrastructure.database.fixture

import com.devh.cafe.infrastructure.database.entity.Option
import com.devh.cafe.infrastructure.database.entity.SubOption
import com.devh.cafe.infrastructure.database.entity.enums.OptionType

val fixtureOptionIceHot = Option(id = 3001, name = "아이스/핫", type = OptionType.CATEGORY, menu = null, category = fixtureCategoryBeverage, displayOrder = 1)
val fixtureOptionShot = Option(id = 3002, name = "샷", type = OptionType.CATEGORY, menu = null, category = fixtureCategoryCoffee, displayOrder = 1)
val fixtureOptionBean = Option(id = 3003, name = "원두", type = OptionType.CATEGORY, menu = null, category = fixtureCategoryCoffee, displayOrder = 2)
val fixtureOptionSugar = Option(id = 3004, name = "당도", type = OptionType.CATEGORY, menu = null, category = fixtureCategoryFlatccino, displayOrder = 1)
val fixtureOptionMilk = Option(id = 3005, name = "원유", type = OptionType.MENU, menu = fixtureMenuLatte, category = null, displayOrder = 1)
val fixtureSubOptionIce = SubOption(id = 4001, name = "아이스", price = 500, displayOrder = 1, option = fixtureOptionIceHot)
val fixtureSubOptionHot = SubOption(id = 4002, name = "핫", price = 0, displayOrder = 2, option = fixtureOptionIceHot)
val fixtureSubOptionDefaultShot = SubOption(id = 4003, name = "기본", price = 0, displayOrder = 1, option = fixtureOptionShot)
val fixtureSubOptionOneShot = SubOption(id = 4004, name = "1샷", price = 500, displayOrder = 2, option = fixtureOptionShot)
val fixtureSubOptionTwoShot = SubOption(id = 4005, name = "2샷", price = 1000, displayOrder = 3, option = fixtureOptionShot)
val fixtureSubOptionDarkBean = SubOption(id = 4006, name = "다크", price = 0, displayOrder = 1, option = fixtureOptionBean)
val fixtureSubOptionAcidBean = SubOption(id = 4007, name = "산미", price = 0, displayOrder = 2, option = fixtureOptionBean)
val fixtureSubOptionMoreSugar = SubOption(id = 4008, name = "더 달게", price = 0, displayOrder = 1, option = fixtureOptionSugar)
val fixtureSubOptionLessSugar = SubOption(id = 4009, name = "덜 달게", price = 0, displayOrder = 2, option = fixtureOptionSugar)
val fixtureSubOptionOriginalMilk = SubOption(id = 4010, name = "우유", price = 0, displayOrder = 1, option = fixtureOptionMilk)
val fixtureSubOptionSoyMilk = SubOption(id = 4011, name = "두유", price = 0, displayOrder = 2, option = fixtureOptionMilk)
val fixtureSubOptionOatMilk = SubOption(id = 4012, name = "오트밀", price = 0, displayOrder = 3, option = fixtureOptionMilk)

val fixtureOptionsAmericano = mutableListOf(
        fixtureOptionIceHot, fixtureOptionShot, fixtureOptionBean
)
