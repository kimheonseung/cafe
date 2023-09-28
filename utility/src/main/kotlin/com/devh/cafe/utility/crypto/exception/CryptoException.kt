package com.devh.cafe.utility.crypto.exception

class CryptoException(
    private val msg: String
) : IllegalArgumentException(msg)
