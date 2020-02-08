package com.mmalek.jsonSql.execution.runnables.operators

import com.mmalek.jsonSql.execution.runnables.Folders._
import com.mmalek.jsonSql.execution.runnables.Runnable
import com.mmalek.jsonSql.execution.runnables.Types.RunnableArgument
import com.mmalek.jsonSql.jsonParsing.dataStructures.JValue
import shapeless.Coproduct

class GreaterThanOperator extends Runnable {
  def canRun(symbol: String, args: Seq[RunnableArgument]): Boolean =
    symbol == ">" && args.length >= 2 && args.forall(_.fold(IsNumeric))

  def run(allArgs: Seq[RunnableArgument], json: Option[JValue] = None): Option[(RunnableArgument, Int)] = {
    val args = allArgs.takeRight(2)

    if (args.forall(_.fold(IsNumeric)))
      (for {
        n1 <- args.head.fold(RunnableArgumentToNumber)
        n2 <- args.last.fold(RunnableArgumentToNumber)
      } yield Coproduct[RunnableArgument](n1 > n2)).map(r => (r, 2))
    else None
  }
}
