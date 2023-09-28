package com.devh.cafe.utility.crypto

import com.devh.cafe.utility.crypto.exception.CryptoException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CryptoUtilsTest {

    private val cryptoUtils = CryptoUtils(SECRET="secret")

    @Test
    fun 인코딩_성공_테스트() {
        // given
        val givenString = "test"
        // when
        val encrypted = cryptoUtils.encrypt(givenString)
        // then
        println(encrypted)
        Assertions.assertNotNull(encrypted)
    }

    @Test
    fun 인코딩_실패_테스트() {
        // given
        val givenString1 = ""
        val givenString2 = " "
        // when & then
        Assertions.assertAll(
            { Assertions.assertThrows(CryptoException::class.java) { cryptoUtils.encrypt(givenString1) } },
            { Assertions.assertThrows(CryptoException::class.java) { cryptoUtils.encrypt(givenString2) } },
        )
    }

    @Test
    fun 디코딩_성공_테스트() {
        // given
        val givenDecrypted = "test"
        val givenEncrypted = cryptoUtils.encrypt(givenDecrypted)
        // when
        val decrypted = cryptoUtils.decrypt(givenEncrypted)
        // then
        println(decrypted)
        Assertions.assertAll(
            { Assertions.assertNotNull(decrypted) },
            { Assertions.assertEquals(decrypted, givenDecrypted) },
        )
        Assertions.assertNotNull(decrypted)
        Assertions.assertNotNull(decrypted)
    }

    @Test
    fun 디코딩_실패_테스트() {
        // given
        val givenString1 = ""
        val givenString2 = " "
        // when & then
        Assertions.assertAll(
            { Assertions.assertThrows(CryptoException::class.java) { cryptoUtils.decrypt(givenString1) } },
            { Assertions.assertThrows(CryptoException::class.java) { cryptoUtils.decrypt(givenString2) } },
        )
    }
}
