package com.myra.invest
package config

object CompanySymbolMapper {

  // keyword -> (symbol, company name)
  private val companyMapping: Map[String, (String, String)] = Map(

    // Reliance
    "Reliance Industries" -> ("RELIANCE.BSE", "Reliance Industries"),
    "Reliance Industries Ltd" -> ("RELIANCE.BSE", "Reliance Industries"),
    "Reliance" -> ("RELIANCE.BSE", "Reliance Industries"),

    // TCS
    "Tata Consultancy Services" -> ("TCS.BSE", "Tata Consultancy Services"),
    "TCS" -> ("TCS.BSE", "Tata Consultancy Services"),

    // Infosys
    "Infosys Ltd" -> ("INFY.BSE", "Infosys"),
    "Infosys" -> ("INFY.BSE", "Infosys"),

    // HDFC Bank
    "HDFC Bank Ltd" -> ("HDFCBANK.BSE", "HDFC Bank"),
    "HDFC Bank" -> ("HDFCBANK.BSE", "HDFC Bank"),

    // ICICI Bank
    "ICICI Bank Ltd" -> ("ICICIBANK.BSE", "ICICI Bank"),
    "ICICI Bank" -> ("ICICIBANK.BSE", "ICICI Bank")
  )

  def lookupCompany(newsText: String): Option[(String, String)] = {

    val normalizedText = newsText.toLowerCase

    companyMapping.collectFirst{
      case (keyword, companyDetails)
        if normalizedText.contains(keyword.toLowerCase) => companyDetails
    }
  }
}
