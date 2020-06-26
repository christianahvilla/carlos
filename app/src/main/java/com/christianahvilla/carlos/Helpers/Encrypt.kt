package com.christianahvilla.carlos.Helpers

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

open class Encrypt {

     fun encrypt(password: String?): String? {
        return try {
            val keySpec: SecretKeySpec = generateKey(password)
            val c: Cipher = Cipher.getInstance("AES")
            c.init(Cipher.ENCRYPT_MODE, keySpec)
            val encVal: ByteArray = c.doFinal(password!!.toByteArray())
            Base64.encodeToString(encVal, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @Throws(Exception::class)
    protected fun generateKey(password: String?): SecretKeySpec {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val bytes: ByteArray = password!!.toByteArray(StandardCharsets.UTF_8)
        digest.update(bytes, 0, bytes.size)
        val key: ByteArray = digest.digest()
        return SecretKeySpec(key, "AES")
    }

}