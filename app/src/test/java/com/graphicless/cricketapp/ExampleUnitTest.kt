package com.graphicless.cricketapp

import org.junit.Test
import org.junit.Assert.*
import java.net.HttpURLConnection
import java.net.URL

class ExampleUnitTest {

    @Test
    fun testHttpGetStatusCode() {
        val url = "https://cricket.sportmonks.com/api/v2.0/positions?api_token=RPrn0e15lzWEqcFHvbcwVaPqPsSKR8Dv1fcxFHkWWAidr1bJZ5NyfTfBUPTP"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()

        // Check if the status code of the URL is 200
        assertEquals(HttpURLConnection.HTTP_OK, connection.responseCode)

        // Disconnect the connection
        connection.disconnect()
    }

}


