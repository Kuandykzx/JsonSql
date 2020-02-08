package com.mmalek.jsonSql.execution.runnables.functions

import com.mmalek.jsonSql.execution.runnables.Runnable
import com.mmalek.jsonSql.execution.runnables.Types.RunnableArgument
import com.mmalek.jsonSql.extensions.JValueOps._
import com.mmalek.jsonSql.jsonParsing.dataStructures.{JNumber, JValue}
import com.mmalek.jsonSql.sqlParsing.Token.Field
import shapeless.{Coproduct, Poly1}

class AvgFunction extends Runnable {
  def canRun(symbol: String, args: Seq[RunnableArgument]): Boolean =
    symbol == "avg" && args.nonEmpty && args.head.fold(RunnableArgumentToBool)

  def run(args: Seq[RunnableArgument], json: Option[JValue]): Option[(RunnableArgument, Int)] =
    if (json.isEmpty) None
    else
      args
        .last
        .fold(RunnableArgumentToValueOption)
        .map(fieldName => json.get.getValuesFor(fieldName.split("\\.")))
        .flatMap(values => {
          if(hasInvalidValues(values)) None
          else {
            val numbers = values.flatten.map {
              case JNumber(v) => v
              case _ => BigDecimal(0)
            }

            if (numbers.isEmpty) None
            else Some(Coproduct[RunnableArgument](numbers.sum / numbers.length), 1)
          }
        })

  private def hasInvalidValues(values: Seq[Option[JValue]]) =
    values.exists {
      case Some(JNumber(_)) | None => false
      case _ => true
    }

  object RunnableArgumentToBool extends Poly1 {
    implicit val atField: Case.Aux[Field, Boolean] = at { _: Field => true }
    implicit val atDouble: Case.Aux[BigDecimal, Boolean] = at { _: BigDecimal => false }
    implicit val atString: Case.Aux[String, Boolean] = at { _: String => false }
    implicit val atBool: Case.Aux[Boolean, Boolean] = at { _: Boolean => false }
  }

  object RunnableArgumentToValueOption extends Poly1 {
    implicit val atField: Case.Aux[Field, Option[String]] = at { x: Field => Some(x.value) }
    implicit val atDouble: Case.Aux[BigDecimal, Option[String]] = at { _: BigDecimal => None }
    implicit val atString: Case.Aux[String, Option[String]] = at { _: String => None }
    implicit val atBool: Case.Aux[Boolean, Option[String]] = at { _: Boolean => None }
  }
}
