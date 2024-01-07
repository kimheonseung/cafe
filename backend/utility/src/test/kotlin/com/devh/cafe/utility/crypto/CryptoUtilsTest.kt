package com.devh.cafe.utility.crypto

import com.devh.cafe.utility.crypto.exception.CryptoException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldNotBe

class CryptoUtilsTest : BehaviorSpec({

    val cryptoUtils = CryptoUtils(SECRET="secret")

    given("cryptoUtils의 secret이 설정되고, 원시 문자열이 주어질 때") {
        val givenString = "test"
        `when`("암호화를 하면") {
            val encrypted = cryptoUtils.encrypt(givenString)
            then("null이 아닌 문자열이 반환된다.") {
                println(encrypted)
                encrypted shouldNotBe null
            }
        }
    }

    given("cryptoUtils의 secret이 설정되고, 잘못된 원시 문자열이 주어질 때") {
        val givenString1 = ""
        val givenString2 = " "
        `when`("암호화를 하면") {
            shouldThrow<CryptoException> {
                cryptoUtils.encrypt(givenString1)
                cryptoUtils.encrypt(givenString2)
            }
        }
    }

    given("cryptoUtils의 secret이 설정되고, 암호화된 문자열이 주어질 때") {
        val givenDecrypted = "test"
        val givenEncrypted = cryptoUtils.encrypt(givenDecrypted)
        `when`("복호화를 하면") {
            val decrypted = cryptoUtils.decrypt(givenEncrypted)
            then("원래 문자열과 일치한다.") {
                decrypted shouldNotBe null
                decrypted shouldBeEqual givenDecrypted
            }
        }
    }

    given("cryptoUtils의 secret이 설정되고, 잘못된 암호화된 문자열이 주어질 때") {
        val givenString1 = ""
        val givenString2 = " "
        `when`("복호화를 하면") {
            shouldThrow<CryptoException> {
                cryptoUtils.encrypt(givenString1)
                cryptoUtils.encrypt(givenString2)
            }
        }
    }
})
