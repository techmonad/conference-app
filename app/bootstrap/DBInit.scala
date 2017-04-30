package bootstrap

import javax.inject.Inject

import models.Conference
import play.Logger
import repository.ConferenceRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.control.NonFatal


class DBInit @Inject()(conferenceRepo: ConferenceRepository)(implicit ec: ExecutionContext) {

  def insert = for {
    conferences <- conferenceRepo.getAll() if (conferences.length == 0)
    _ <- conferenceRepo.createAll(SampleData.conferences)
  } yield {}

  try {
    Logger.info("Initializing database................")
    Await.result(insert, Duration.Inf)
  } catch {
    case NonFatal(th) =>
      Logger.error("Error in Initializing database ", th)
  }

}

object SampleData {

  val conferences = List(
    Conference("Scala Days", "scaladays@gmail.com", "Scala Days Conference", "April 18th, 2017", "April 21st 2017", "Chicago"),
    Conference("Spark Summit", "sparksummit@spark.com", "Spark Summit Conference", "FEBRUARY 7th, 2017", "FEBRUARY 9th, 2017", "Boston")
  )

}