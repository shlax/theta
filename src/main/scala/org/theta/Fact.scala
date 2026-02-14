package org.theta

/**
 * capital(country:austria, city:vienna)
 */
case class Fact(relation:String, arguments:Map[String, Atom]){

  def evaluate(binding: Binding, queryable: Queryable): Unit = {
    evaluate(binding)
  }

  /** check if signature matches binding */
  def evaluate(binding: Binding): Unit = {
    if(arguments.size == binding.size){
      binding.push {
        if (arguments.forall { (key, atom) => binding.merge(key, atom) }) {
          binding.onMatch()
        }
      }
    }
  }

}
