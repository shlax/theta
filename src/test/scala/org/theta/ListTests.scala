package org.theta

import org.junit.jupiter.api.{Assertions, Test}
import org.theta.core.{EqualClause, ListClause, SetClause}
import org.theta.solver.{Binding, Variable}
import org.theta.dsl.builder.*

class ListTests {

  @Test
  def test01(): Unit = {
    val db = database{
      add(ListClause(), EqualClause())

      rule("contains", "head" -> "?", "tail" -> "t", "element" -> "e") {
        statement("contains", "list" -> "t", "element" -> "e" )
      }

      rule("contains", "head" -> "h", "tail" -> "?", "element" -> "e") {
        statement("==", "x" -> "h", "y" -> "e" )
      }

      rule("contains", "list" -> "l", "element" -> "e") {
        statement("[|]", "list" -> "l", "head" -> "h", "tail" -> "t" )
        statement("contains", "head" -> "h", "tail" -> "t", "element" -> "e" )
      }

    }

    val numbers = List(1,2,3)
    val list = Variable(numbers)

    for( i <- numbers) {
      val element = Variable(i)
      val binding = Binding(Map("list" -> list, "element" -> element), db)

      var res = 0
      db.query("contains", binding) {
        res += element.resolve.asInstanceOf[Int]
      }

      Assertions.assertEquals(i, res)
    }

    val element = Variable(4)
    val binding = Binding(Map("list" -> list, "element" -> element), db)

    var res = 0
    db.query("contains", binding) {
      res += 1
    }

    Assertions.assertEquals(0, res)

  }

  @Test
  def test02(): Unit = {
    val db = database{
      add(ListClause(), SetClause())

      rule("iterate", "head" -> "?", "tail" -> "t", "element" -> "e") {
        statement("iterate", "list" -> "t", "element" -> "e" )
      }

      rule("iterate", "head" -> "h", "tail" -> "?", "element" -> "e") {
        statement("=", "x" -> "h", "y" -> "e" )
      }

      rule("iterate", "list" -> "l", "element" -> "e") {
        statement("[|]", "list" -> "l", "head" -> "h", "tail" -> "t" )
        statement("iterate", "head" -> "h", "tail" -> "t", "element" -> "e" )
      }

    }

    val numbers = List(1,2,3)
    val list = Variable(numbers)

    val element = Variable()
    val binding = Binding(Map("list" -> list, "element" -> element), db)

    var res:List[Int] = Nil
    db.query("iterate", binding) {
      val i = element.resolve.asInstanceOf[Int]
      res = i :: res
    }

    Assertions.assertEquals(numbers.size, res.size)
    Assertions.assertEquals(numbers.toSet, res.toSet)

  }

}
