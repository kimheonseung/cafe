package com.devh.cafe.utility.crypto

import com.devh.cafe.utility.crypto.exception.CryptoException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Component
class CryptoUtils(
    @Value("{crypto.aes.secret}")
    val SECRET: String,
) {

    fun encrypt(s: String): String {
        if(s.isNotBlank()) {
            val saltBytes = createSaltBytes()
            val secretKeySpec = newSecretKeySpec(saltBytes)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val algorithmParameters = cipher.parameters
            val encryptedTextBytes: ByteArray = cipher.doFinal(s.toByteArray(Charsets.UTF_8))

            /* Initial Vector(1단계 암호화 블록용) */
            val ivBytes = algorithmParameters.getParameterSpec(IvParameterSpec::class.java).iv

            /* salt + Initial Vector + 암호문 결합 후 Base64인코딩 */
            val buffer = ByteArray(saltBytes.size + ivBytes.size + encryptedTextBytes.size)
            System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.size)
            System.arraycopy(ivBytes, 0, buffer, saltBytes.size, ivBytes.size);
            System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.size + ivBytes.size, encryptedTextBytes.size);

            return Base64.getEncoder().encodeToString(buffer)
        }
        throw CryptoException("target string is blank.")
    }

    fun decrypt(s: String): String {
        if(s.isNotBlank()) {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val byteBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(s))

            val saltBytes = createSaltBytes()
            byteBuffer.get(saltBytes, 0, saltBytes.size)

            val ivBytes = ByteArray(cipher.blockSize)
            byteBuffer.get(ivBytes, 0, ivBytes.size)

            val encryptedTextBytes = ByteArray(byteBuffer.capacity() - saltBytes.size - ivBytes.size)
            byteBuffer.get(encryptedTextBytes)

            val secretKeySpec = newSecretKeySpec(saltBytes)

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(ivBytes))

            return String(cipher.doFinal(encryptedTextBytes))
        }
        throw CryptoException("target string is blank.")
    }

    private fun createSaltBytes(): ByteArray {
        val random = SecureRandom()
        val saltBytes = ByteArray(20)
        random.nextBytes(saltBytes)
        return saltBytes
    }

    private fun newSecretKeySpec(saltBytes: ByteArray): SecretKeySpec {
        /* Password-Based Key Derivation function */
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        /* 1000번 해시하여 256 bit 키 spec 생성*/
        val spec = PBEKeySpec(this.SECRET.toCharArray(), saltBytes, 1000, 256)
        /* 비밀키 생성 */
        val secretKey = factory.generateSecret(spec)
        return SecretKeySpec(secretKey.encoded, "AES")
    }

}
