package org.ergoplatform.dex.markets.api.v1

import org.ergoplatform.common.http.HttpError
import org.ergoplatform.common.models.TimeWindow
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{
  emptyOutputAs,
  endpoint,
  oneOf,
  oneOfDefaultMapping,
  oneOfMapping,
  query,
  Endpoint,
  EndpointInput,
  Schema,
  Validator
}
import sttp.tapir.generic.auto._

package object endpoints {

  implicit def schemaBigInt: Schema[BigInt] = Schema.schemaForBigDecimal.map(_.toBigIntExact)(BigDecimal(_))

  val VersionPrefix = "v1"

  val baseEndpoint: Endpoint[Unit, HttpError, Unit, Any] =
    endpoint
      .in(VersionPrefix)
      .errorOut(
        oneOf[HttpError](
          oneOfMapping(StatusCode.NotFound, jsonBody[HttpError.NotFound].description("not found")),
          oneOfMapping(StatusCode.NoContent, emptyOutputAs(HttpError.NoContent)),
          oneOfDefaultMapping(jsonBody[HttpError.Unknown].description("unknown"))
        )
      )

  def timeWindow: EndpointInput[TimeWindow] =
    (query[Option[Long]]("from")
      .description("Window lower bound (UNIX timestamp millis)")
      .validateOption(Validator.min(0L)) and
      query[Option[Long]]("to")
        .description("Window upper bound (UNIX timestamp millis)")
        .validateOption(Validator.min(0L)))
      .map { input =>
        TimeWindow(input._1, input._2)
      } { case TimeWindow(from, to) => from -> to }
}