package bootstrap

import models.Conference
import play.api.Logging
import repository.ConferenceRepository

import javax.inject.Inject
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.control.NonFatal


class DBInit @Inject()(conferenceRepo: ConferenceRepository)(implicit ec: ExecutionContext) extends Logging {

  def insert: Future[Unit] = for {
    conferences <- conferenceRepo.getAll() if conferences.isEmpty
    _ <- conferenceRepo.createAll(SampleData.conferences)
  } yield {}

  try {
    logger.info("Initializing database................")
    Await.result(insert, Duration.Inf)
  } catch {
    case NonFatal(th) =>
      logger.error("Error in Initializing database ", th)
  }

}

object SampleData {

  val conferences = List(
    Conference("Scala Days", "scaladays@gmail.com", "Scala Days Conference", "April 18th, 2017", "April 21st 2017", "Chicago"),
    Conference("Spark Summit", "sparksummit@spark.com", "Spark Summit Conference", "FEBRUARY 7th, 2017", "FEBRUARY 9th, 2017", "Boston")
  )

}