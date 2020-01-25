package com.mmalek.jsonSql.execution.runnables

import com.mmalek.jsonSql.execution.extensions.JValueOps._
import com.mmalek.jsonSql.execution.runnables.Types.RunnableArgument
import com.mmalek.jsonSql.jsonParsing.dataStructures.{JDouble, JInt, JValue}
import com.mmalek.jsonSql.sqlParsing.Token.Field
import com.mmalek.jsonSql.types.{MaybeThat, MaybeThis, Or}
import shapeless.{Coproduct, Poly1}

class AvgFunction extends Runnable {
  def canRun(symbol: String, args: Seq[RunnableArgument]): Boolean =
    symbol == "avg" && args.length == 1 && args.head.fold(RunnableArgumentToBool)

  def run(symbol: String, args: Seq[RunnableArgument], json: JValue): Option[RunnableArgument] =
    args.head
      .fold(RunnableArgumentToValueOption)
      .map {
        case MaybeThis(fieldName) => json.getValuesFor(fieldName.split("."))
        case MaybeThat(values) => values
      }
      .flatMap(values => {
        if(hasInvalidValues(values)) None
        else {
          val numbers = values.flatten.map {
            case JInt(v) => BigDecimal(v)
            case JDouble(v) => BigDecimal(v)
            case _ => BigDecimal(0)
          }
          val avg = numbers.sum / numbers.length

          Some(Coproduct[RunnableArgument](avg))
        }
      })

  private def hasInvalidValues(values: Seq[Option[JValue]]) =
    values.exists {
      case Some(JInt(_)) | Some(JDouble(_)) | None => false
      case _ => true
    }

  object RunnableArgumentToBool extends Poly1 {
    implicit val atField: Case.Aux[Field, Boolean] = at { _: Field => true }
    implicit val atSeq: Case.Aux[Seq[Option[JValue]], Boolean] = at { _: Seq[Option[JValue]] => true }
    implicit val atDouble: Case.Aux[BigDecimal, Boolean] = at { _: BigDecimal => false }
    implicit val atString: Case.Aux[String, Boolean] = at { _: String => false }
  }

  object RunnableArgumentToValueOption extends Poly1 {
    implicit val atField: Case.Aux[Field, Option[Or[String, Seq[Option[JValue]]]]] =
      at { x: Field => Some(MaybeThis(x.value)) }
    implicit val atSeq: Case.Aux[Seq[Option[JValue]], Option[Or[String, Seq[Option[JValue]]]]] =
      at { x: Seq[Option[JValue]] => Some(MaybeThat(x)) }
    implicit val atDouble: Case.Aux[BigDecimal, Option[Or[String, Seq[Option[JValue]]]]] = at { _: BigDecimal => None }
    implicit val atString: Case.Aux[String, Option[Or[String, Seq[Option[JValue]]]]] = at { _: String => None }
  }
}
