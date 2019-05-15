package com.smartchain.server.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class Utils {
    fun getAnimals(): MutableList<String> {
        var input: InputStream? = null
        val animals = ArrayList<String>()

        try {
            input = this.javaClass.getResourceAsStream("/animals.txt")
            // load the properties file
            val br = BufferedReader(InputStreamReader(input, "UTF-8"))
            val lines = br.readLines()
            animals.addAll(lines)

        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        // get the property value and return it
        return animals
    }
}