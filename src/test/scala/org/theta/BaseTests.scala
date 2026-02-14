package org.theta

import org.junit.jupiter.api.{Assertions, Test}

class BaseTests {

  @Test
  def ruleTest01(): Unit = {
    val country = Variable()
    val city = Variable()
    val binding = Binding("capital", Map("country" -> country, "city" -> city))

    val rule = Rule("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    val res = rule.evaluate(binding)

    Assertions.assertTrue(res)
    Assertions.assertEquals("austria", country.resolve)
    Assertions.assertEquals("vienna", city.resolve)
  }

  @Test
  def ruleTest02(): Unit = {
    val country = Variable()
    val binding = Binding("capital", Map("country" -> country, "city" -> Value("vienna")))

    val ruleFail = Rule("capital", Map("country" -> Value("italy"), "city" -> Value("rome")))
    val resFail = ruleFail.evaluate(binding)

    Assertions.assertFalse(resFail)
    Assertions.assertFalse(country.get.isDefined)

    val ruleOk = Rule("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    val resOk = ruleOk.evaluate(binding)

    Assertions.assertTrue(resOk)
    Assertions.assertEquals("austria", country.resolve)
  }

}
