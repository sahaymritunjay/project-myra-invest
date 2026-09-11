package com.myra.invest
package utils

import java.net.{HttpURLConnection, URL}
import java.nio.charset.StandardCharsets
import scala.io.Source

object HttpClient {

  def get(url: String): String = {

    val connection =
      new URL(url).openConnection().asInstanceOf[HttpURLConnection]

    connection.setRequestMethod("GET")

    connection.setConnectTimeout(10000)
    connection.setReadTimeout(15000)

    connection.setRequestProperty(
      "User-Agent",
      "Project-Myra-Invest/1.0"
    )

    val responseCode = connection.getResponseCode

    responseCode match {

      case 200 =>
        val response =
          Source.fromInputStream(
            connection.getInputStream,
            StandardCharsets.UTF_8.name()
          ).mkString

        connection.disconnect()
        response

      case code =>
        connection.disconnect()

        throw new RuntimeException(
          s"HTTP Request Failed. Status Code = $code"
        )
    }
  }

}
