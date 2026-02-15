package org.theta

import org.junit.jupiter.api.{Assertions, Test}

class BaseTests {

  @Test
  def ruleTest01(): Unit = {
    val canFly = Fact("can-fly", Map("y" -> Value("turaco")))

    val bird = Rule("bird", Map("x" -> Reference("X")), List(
      Statement("can-fly", Map("y" -> Reference("X")))
    ))

    val db = Database(canFly, bird)

    val name = Variable()
    val binding = Binding(Map("x" -> name), db)

    var res = false
    bird.evaluate(binding) {
      Assertions.assertEquals("turaco", name.resolve)
      res = true
    }

    Assertions.assertTrue(name.value.isEmpty)
    Assertions.assertTrue(res)
  }

  @Test
  def ruleTest02(): Unit = {
    val canFly = Fact("can-fly", Map("y" -> Value("turaco")))
    val animal = Fact("animal", Map("z" -> Value("turaco")))

    val plane = Fact("can-fly", Map("y" -> Value("plane")))
    val dog = Fact("animal", Map("z" -> Value("dog")))

    val bird = Rule("bird", Map("x" -> Reference("X")), List(
      Statement("can-fly", Map("y" -> Reference("X"))),
      Statement("animal", Map("z" -> Reference("X")))
    ))

    val db = Database(plane, dog, canFly, animal, bird)

    val name = Variable()
    val binding = Binding(Map("x" -> name), db)

    var res = false
    bird.evaluate(binding){
      Assertions.assertEquals("turaco", name.resolve)
      res = true
    }

    Assertions.assertTrue(name.value.isEmpty)
    Assertions.assertTrue(res)
  }

  @Test
  def factTest01(): Unit = {
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

    Assertions.assertTrue(country.value.isEmpty)
    Assertions.assertTrue(city.value.isEmpty)
    Assertions.assertTrue(res)
  }

  @Test
  def factTest02(): Unit = {
    val country = Variable()

    var res = false
    val binding = Binding(Map("country" -> country, "city" -> Variable("vienna")), Database())

    val ruleFail = Fact("capital", Map("country" -> Value("italy"), "city" -> Value("rome")))
    ruleFail.evaluate(binding){
      res = true
    }

    Assertions.assertTrue(country.value.isEmpty)
    Assertions.assertFalse(res)

    val ruleOk = Fact("capital", Map("country" -> Value("austria"), "city" -> Value("vienna")))
    ruleOk.evaluate(binding){
      Assertions.assertEquals("austria", country.resolve)
      res = true
    }

    Assertions.assertTrue(country.value.isEmpty)
    Assertions.assertTrue(res)

  }

}
