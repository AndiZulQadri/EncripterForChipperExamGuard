package com.macca.encriptercgm
class LayeredCipher {
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789./:"
    private val caesarShift = 5
    private val substitutionKey = mapOf(
        'A' to 'X', 'B' to 'Y', 'C' to 'Z', 'D' to 'A', 'E' to 'B',
        'F' to 'C', 'G' to 'D', 'H' to 'E', 'I' to 'F', 'J' to 'G',
        'K' to 'H', 'L' to 'I', 'M' to 'J', 'N' to 'K', 'O' to 'L',
        'P' to 'M', 'Q' to 'N', 'R' to 'O', 'S' to 'W', 'T' to 'U',
        'U' to 'V', 'V' to 'P', 'W' to 'Q', 'X' to 'R', 'Y' to 'S',
        'Z' to 'T',
        'a' to 'x', 'b' to 'y', 'c' to 'z', 'd' to 'a', 'e' to 'b',
        'f' to 'c', 'g' to 'd', 'h' to 'e', 'i' to 'f', 'j' to 'g',
        'k' to 'h', 'l' to 'i', 'm' to 'j', 'n' to 'k', 'o' to 'l',
        'p' to 'm', 'q' to 'n', 'r' to 'o', 's' to 'w', 't' to 'u',
        'u' to 'v', 'v' to 'p', 'w' to 'q', 'x' to 'r', 'y' to 's',
        'z' to 't',
        '0' to '9', '1' to '8', '2' to '7', '3' to '6', '4' to '5',
        '5' to '4', '6' to '3', '7' to '2', '8' to '1', '9' to '0',
        '.' to '.', ':' to ':', '/' to '/', '_' to '_'
    )



    fun encrypt(input: String): String {
        // Jika string dimulai dengan "https://", lewati 8 karakter pertama
        if (input.startsWith("https://")) {
            return "https://" + substitutionEncrypt(caesarEncrypt(input)).drop(8)
        }
        return substitutionEncrypt(caesarEncrypt(input))
    }

    fun decrypt(input: String): String {
        if (input.startsWith("https://")) {
            return "https://" + caesarDecrypt(substitutionDecrypt(input)).drop(8)
        }
        return caesarDecrypt(substitutionDecrypt(input))
    }

    private fun caesarEncrypt(input: String): String {
        return input.map { char ->
            val index = (alphabet.indexOf(char) + caesarShift) % alphabet.length
            alphabet[index]
        }.joinToString("")
    }

    private fun caesarDecrypt(input: String): String {
        return input.map { char ->
            val index = (alphabet.indexOf(char) - caesarShift + alphabet.length) % alphabet.length
            alphabet[index]
        }.joinToString("")
    }

    private fun substitutionEncrypt(input: String): String {
        return input.map { char ->
            substitutionKey[char] ?: char
        }.joinToString("")
    }

    private fun substitutionDecrypt(input: String): String {
        return input.map { char ->
            substitutionKey.entries.find { it.value == char }?.key ?: char
        }.joinToString("")
    }
}