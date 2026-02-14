package org.theta

import org.junit.jupiter.api.{Assertions, Test}

class BaseTests {

  @Test
  def ruleTest01(): Unit = {
    val country = Variable()
    val city = Variable()

    var res = false
    val binding = Binding(Map("country" -> country, "city" -> city), { () =>
      Assertions.assertEquals("austria", country.resolve)
      Assertions.assertEquals("vienna", city.resolve)
      res = true
    })

    val rule = Fact("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    rule.evaluate(binding)
    Assertions.assertTrue(res)
  }

  @Test
  def ruleTest02(): Unit = {
    val country = Variable()

    var res = false
    val binding = Binding(Map("country" -> country, "city" -> Value("vienna")), { () =>
      Assertions.assertEquals("austria", country.resolve)
      res = true
    })

    val ruleFail = Fact("capital", Map("country" -> Value("italy"), "city" -> Value("rome")))
    ruleFail.evaluate(binding)
    Assertions.assertFalse(res)

    val ruleOk = Fact("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    ruleOk.evaluate(binding)
    Assertions.assertTrue(res)

  }

}
