package com.devh.cafe.infrastructure.database.fixture

import com.devh.cafe.infrastructure.database.entity.Authority

val fixtureAuthorityNormal = Authority(id = 1001, name = "일반")

fun newAuthority(): Authority {
    return Authority(name = "authority-${Math.random()}")
}
