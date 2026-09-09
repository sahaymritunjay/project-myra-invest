package com.myra.invest
package ingestion.providers

import model.StockPrice

trait Provider {

  def fetch(symbol: String): StockPrice
}
