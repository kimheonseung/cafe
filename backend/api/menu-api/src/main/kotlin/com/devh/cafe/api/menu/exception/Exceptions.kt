package com.devh.cafe.api.menu.exception

val MSG_CATEGORY_NOT_EXISTS = "존재하지 않는 카테고리입니다."
fun categoryDoesNotExists(): CategoryException {
    return CategoryException(MSG_CATEGORY_NOT_EXISTS)
}

val MSG_CATEGORY_ALREADY_EXISTS = "이미 존재하는 카테고리입니다."
fun categoryAlreadyExists(): CategoryException {
    return CategoryException(MSG_CATEGORY_ALREADY_EXISTS)
}

val MSG_MENU_NOT_EXISTS = "존재하지 않는 메뉴입니다."
fun menuDoesNotExists(): MenuException {
    return MenuException(MSG_MENU_NOT_EXISTS)
}

val MSG_MENU_ALREADY_EXISTS = "이미 존재하는 메뉴입니다."
fun menuAlreadyExists(): MenuException {
    return MenuException(MSG_MENU_ALREADY_EXISTS)
}

val MSG_OPTION_NOT_EXISTS = "존재하지 않는 옵션입니다."
fun optionDoesNotExists(): OptionException {
    return OptionException(MSG_OPTION_NOT_EXISTS)
}

val MSG_OPTION_ALREADY_EXISTS = "이미 존재하는 옵션입니다."
fun optionAlreadyExists(): OptionException {
    return OptionException(MSG_OPTION_ALREADY_EXISTS)
}

val MSG_SUB_OPTION_NOT_EXISTS = "존재하지 않는 하위 옵션입니다."
fun subOptionDoesNotExists(): SubOptionException {
    return SubOptionException(MSG_SUB_OPTION_NOT_EXISTS)
}

val MSG_SUB_OPTION_ALREADY_EXISTS = "이미 존재하는 하위 옵션입니다."
fun subOptionAlreadyExists(): SubOptionException {
    return SubOptionException(MSG_SUB_OPTION_ALREADY_EXISTS)
}
