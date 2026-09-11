package com.myra.invest
package config

object CompanySymbolMapperTest extends  App {

  val headlines = Seq(
    "Reliance Industries announces a new green hydrogen investment.",
    "Infosys signs a billion-dollar AI transformation contract.",
    "HDFC Bank launches a new digital banking platform.",
    "ICICI Bank reports record quarterly profit.",
    "Apple launches a new iPhone."
  )

  headlines.foreach { headline =>

    println("----------------------------------------")
    println(s"Headline : $headline")

    CompanySymbolMapper.lookupCompany(headline) match {

      case Some((symbol, company)) =>
        println(s"Mapped Company : $company")
        println(s"Mapped Symbol  : $symbol")

      case None =>
        println("No Project Myra stock matched.")
    }
  }
}
