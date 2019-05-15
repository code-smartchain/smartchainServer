package com.smartchain.server.utils

import java.io.*
import java.util.ArrayList

class Utils {
    fun getAnimals(): MutableList<String> {
        var input: InputStream? = null
        val animals = ArrayList<String>()

        try {
            input = FileInputStream("animals.txt")
            // load the properties file
            val br = BufferedReader(InputStreamReader(input, "UTF-8"))
            val line = br.readLine()
            animals.add(line)

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