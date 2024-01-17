package com.fynn.logbook.util

import java.security.SecureRandom
import java.util.Random
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.ln


object NanoIdUtil {
    private val DEFAULT_NUMBER_GENERATOR = SecureRandom()
    private val DEFAULT_ALPHABET =
        "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    const val DEFAULT_SIZE = 21

    fun randomNanoId(
        random: Random = DEFAULT_NUMBER_GENERATOR,
        alphabet: CharArray = DEFAULT_ALPHABET,
        size: Int = DEFAULT_SIZE
    ): String {
//        requireNotNull(random) { "random cannot be null." }
//        requireNotNull(alphabet) { "alphabet cannot be null." }
        if (alphabet.isNotEmpty() && alphabet.size < 256) {
            require(size > 0) { "size must be greater than zero." }
            val mask =
                (2 shl floor(ln((alphabet.size - 1).toDouble()) / ln(2.0))
                    .toInt()) - 1
            val step =
                ceil(1.6 * mask.toDouble() * size.toDouble() / alphabet.size.toDouble())
                    .toInt()
            val idBuilder = StringBuilder()
            while (true) {
                val bytes = ByteArray(step)
                random.nextBytes(bytes)
                for (i in 0 until step) {
                    val alphabetIndex = bytes[i].toInt() and mask
                    if (alphabetIndex < alphabet.size) {
                        idBuilder.append(alphabet[alphabetIndex])
                        if (idBuilder.length == size) {
                            return idBuilder.toString()
                        }
                    }
                }
            }
        } else {
            throw IllegalArgumentException("alphabet must contain between 1 and 255 symbols.")
        }
    }
}