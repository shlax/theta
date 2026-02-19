package org.theta.core

import org.theta.solver.Clause

abstract class Operator(name:String, args:String *) extends Clause{

  override def relation: String = name

  override def arguments: Set[String] = args.toSet

}
