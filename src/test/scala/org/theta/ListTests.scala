package org.theta

import org.junit.jupiter.api.{Assertions, Test}
import org.theta.core.{EqualClause, ListClause, SetClause}
import org.theta.solver.{Binding, Reference, Value, Variable}
import org.theta.dsl.builder.*

class ListTests {

  @Test
  def list01(): Unit = {
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
        res += 1
        true
      }

      Assertions.assertEquals(1, res)
    }

    val element = Variable(4)
    val binding = Binding(Map("list" -> list, "element" -> element), db)

    var res = 0
    db.query("contains", binding) {
      res += 1
      true
    }

    Assertions.assertEquals(0, res)

  }

  @Test
  def list02(): Unit = {
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
      true
    }

    Assertions.assertEquals(numbers.size, res.size)
    Assertions.assertEquals(numbers.toSet, res.toSet)

  }

  @Test
  def list03(): Unit = {
    val db = database {
      add(ListClause(), SetClause())

      rule("append", "from" -> Value(Nil), "to" -> Reference("b"), "result" -> Reference("r")) {
        statement("=", "x" -> "b", "y" -> "r" )
      }

      rule("append", "from" -> "a", "to" -> "b", "result" -> "r") {
        statement("[|]", "list" -> "a", "head" -> "h", "tail" -> "t")
        statement("[|]", "list" -> "c", "head" -> "h", "tail" -> "b")
        statement("append", "from" -> "t", "to" -> "c", "result" -> "r")
      }

    }

    val from = Variable( List(3, 2, 1) )
    val to = Variable( List(4) )
    val result = Variable()

    val element = Variable()
    val binding = Binding(Map("from" -> from, "to" -> to, "result" -> result), db)

    var cnt = 0
    var res: List[Int] = Nil
    db.query("append", binding) {
      res = result.resolve.asInstanceOf[List[Int]]
      cnt += 1
      true
    }

    Assertions.assertEquals(1, cnt)
    Assertions.assertEquals(List(1, 2, 3, 4), res)

  }

  @Test
  def list04(): Unit = {

    val db = database {
      add(ListClause(), SetClause())

      rule("reverse", "from" -> Value(Nil), "to" -> Reference("b"), "result" -> Reference("r")) {
        statement("=", "x" -> "b", "y" -> "r")
      }

      rule("reverse", "from" -> "a", "result" -> "r") {
        statement("reverse", "from" -> Reference("a"), "to" -> Value(Nil), "result" -> Reference("r") )
      }

      rule("reverse", "from" -> "a", "to" -> "b", "result" -> "r") {
        statement("[|]", "list" -> "a", "head" -> "h", "tail" -> "t")
        statement("[|]", "list" -> "c", "head" -> "h", "tail" -> "b")
        statement("reverse", "from" -> "t", "to" -> "c", "result" -> "r")
      }

    }

    val from = Variable(List(3, 2, 1))
    val result = Variable()

    val element = Variable()
    val binding = Binding(Map("from" -> from, "result" -> result), db)

    var res = 0
    db.query("reverse", binding) {
      val r = result.resolve.asInstanceOf[List[?]]
      Assertions.assertEquals(List(1, 2, 3), r)
      res += 1
      true
    }

    Assertions.assertEquals(1, res)

  }
}
