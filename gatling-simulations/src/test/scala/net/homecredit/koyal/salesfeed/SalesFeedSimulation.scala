package net.homecredit.koyal.salesfeed

import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import net.homecredit.koyal.common.Utils

import scala.concurrent.duration.DurationInt

class SalesFeedSimulation extends Simulation {

  private val authHeader = Utils.getBasicAuthHeader(SalesFeedConstants.SalesfeedAccessUser,
                                                    SalesFeedConstants.SalesfeedAccessPassword)

  private val httpConf = http.baseUrl(SalesFeedConstants.Url)
      .headers(Map("Accept" -> "application/json",
                   "Authorization" -> authHeader,
                   "X-Test" -> "true"))

  setUp(
    SalesFeed.scenarioBuilder.inject(
      atOnceUsers(SalesFeedConstants.NumberOfUsers),
      ).protocols(httpConf)
    ).maxDuration(DurationInt(SalesFeedConstants.Duration) seconds)
}
