package org.theta

import org.junit.jupiter.api.{Assertions, Test}

class BaseTests {

  @Test
  def ruleTest01(): Unit = {
    val country = Variable()
    val city = Variable()
    val binding = Binding(Map("country" -> country, "city" -> city), Database())

    val rule = Fact("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))

    var res = false
    rule.evaluate(binding){
      Assertions.assertEquals("austria", country.resolve)
      Assertions.assertEquals("vienna", city.resolve)
      res = true
    }

    Assertions.assertTrue(res)
  }

  @Test
  def ruleTest02(): Unit = {
    val country = Variable()

    var res = false
    val binding = Binding(Map("country" -> country, "city" -> Variable("vienna")), Database())

    val ruleFail = Fact("capital", Map("country" -> Value("italy"), "city" -> Value("rome")))
    ruleFail.evaluate(binding){
      Assertions.assertEquals("austria", country.resolve)
      res = true
    }
    Assertions.assertFalse(res)

    val ruleOk = Fact("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    ruleOk.evaluate(binding){
      Assertions.assertEquals("austria", country.resolve)
      res = true
    }
    Assertions.assertTrue(res)

  }

}
