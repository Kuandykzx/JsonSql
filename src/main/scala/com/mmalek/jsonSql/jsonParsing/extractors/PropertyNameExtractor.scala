package com.mmalek.jsonSql.jsonParsing.extractors

class PropertyNameExtractor(builder: StringBuilder) {
  def next(char: Char): PropertyNameExtractor = {
    builder.append(char)

    this
  }

  def isPropertyName: Boolean = builder.nonEmpty && inPropertyName

  def flush: (String, PropertyNameExtractor) = {
    val value = builder.toString
    val firstQuoteIdx = value.indexOf('"')
    val secondQuoteIdx = value.indexOf('"', firstQuoteIdx + 1)

    builder.clear()

    if (firstQuoteIdx > -1 && secondQuoteIdx > -1) {
      val propertyName = value.substring(firstQuoteIdx + 1, secondQuoteIdx)

      (propertyName, new PropertyNameExtractor(builder))
    } else ("", new PropertyNameExtractor(builder))
  }

  def flushBuilder: PropertyNameExtractor = {
    builder.clear()

    this
  }

  private def inPropertyName = {
    val noWhitespaces = builder.toString.replaceAll(" +", "")
    val quotesCount = noWhitespaces.count(c => c == '"')

    if (noWhitespaces.length == 0 || quotesCount < 2) false
    else {
      val firstChar = noWhitespaces.charAt(0)

      firstChar == ',' || firstChar == '{'
    }
  }
}
