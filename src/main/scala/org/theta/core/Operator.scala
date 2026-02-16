package org.theta.core

import org.theta.{Reference, Rule}

class Operator(name:String, arguments:String *) extends Rule(name, arguments.map(x => x -> Reference(x)).toMap )
