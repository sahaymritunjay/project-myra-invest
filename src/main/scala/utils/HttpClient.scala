package com.myra.invest
package utils

import java.net.{HttpURLConnection, URL}
import scala.io.Source

object HttpClient {

  def get(url: String, retryCount:Int = 1): String = {

    var attempt = 0;

    while(attempt <= retryCount)
      {
        try
        {
          val connection = new URL(url).openConnection().asInstanceOf[HttpURLConnection]
          connection.setRequestMethod("GET")
          connection.setRequestProperty("User-Agent","Project-Myra-Invest/1.0")
          connection.setConnectTimeout(10000)
          connection.setReadTimeout(15000)
          val responseCode = connection.getResponseCode

          responseCode match {
            case 200 => val response = Source.fromInputStream(connection.getInputStream).mkString
            connection.disconnect()
            return response

            case 429 =>
              connection.disconnect()
              throw new RuntimeException("Alphavantage rate limit Exceeded.. Wait")

            case code =>
              connection.disconnect()
              throw new RuntimeException(s"HTTP request failed with status code $code")
          }

        }catch {
          case ex: Exception =>
            attempt +=1
            if(attempt > retryCount)
              throw new RuntimeException(s"Failed after ${retryCount + 1} attempts : ${ex.getMessage}")
              println(s"Retrying request... Attempt $attempt")
            Thread.sleep(3000)
        }
      }
    throw new RuntimeException("Unexpected HTTP Failure.")

  }

}
