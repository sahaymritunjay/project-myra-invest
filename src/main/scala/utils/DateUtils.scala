package com.myra.invest
package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils
{

  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def today(): String = LocalDate.now().format(formatter)

  def ninetyDaysAgo(): String = LocalDate.now().minusDays(90).format(formatter)

  def oneYearAgo(): String = LocalDate.now().minusDays(365).format(formatter)

}
