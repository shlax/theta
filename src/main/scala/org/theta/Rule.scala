package org.theta

/**
 * capital(country:austria, city:vienna)
 **/
case class Rule(id:String, arguments:Map[String, Atom]){

  def evaluate(binding: Binding, queryable: Queryable): Boolean = {
    evaluate(binding)
  }
  
  def evaluate(binding: Binding): Boolean = {
    if(id == binding.id && arguments.size == binding.size){
      val sate = binding.state
      if(! arguments.forall{ (key, atom) => binding.merge(key, atom) } ){
        sate.restore()
        false
      }else true
    }else false
  }

}
