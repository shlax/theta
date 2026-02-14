package org.theta

trait Queryable {

  def query(test: Fact => Boolean): List[Fact]

}
