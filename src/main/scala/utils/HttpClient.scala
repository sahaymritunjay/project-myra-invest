package com.myra.invest
package utils

import java.net.{HttpURLConnection, URL}
import scala.io.Source

object HttpClient {

  def get(url: String): String = {

    val connection = new URL(url).openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")
    connection.setRequestProperty("User-Agent","Project-Myra-Invest/1.0")
    connection.setConnectTimeout(10000)
    connection.setReadTimeout(10000)

    val responseCode = connection.getResponseCode

    if(responseCode  !=200)
      {
        throw new RuntimeException(s"HTTP Request Failed. Status Code = $responseCode")
      }

    val response = Source.fromInputStream(connection.getInputStream)

    try response.mkString
    finally response.close()

  }

}
