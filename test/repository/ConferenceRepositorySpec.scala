package repository

import models.Conference
import play.api.Application
import play.api.test.{PlaySpecification, WithApplication}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class ConferenceRepositorySpec extends PlaySpecification {

  "Conference repository" should {

    def conferenceRepo(implicit app: Application) =
      Application.instanceCache[ConferenceRepository].apply(app)

    "get all conferences" in new WithApplication() {
      val conferences = await(conferenceRepo.getAll)
      conferences.length === 2
      conferences.head.name === "Scala Days"
    }

    "get conference by id" in new WithApplication() {
      val result = await(conferenceRepo.getById(1))
      result.isDefined === true
      result.get.name === "Scala Days"
    }

    "create conference" in new WithApplication() {
      val conferenceId = await(conferenceRepo.create(Conference("Spark Summit", "scaladays@gmail.com", "Scala Days Conference", "FEBRUARY 7th, 2017", "FEBRUARY 9th, 2017", "Boston")))
      conferenceId === 3
    }


    "update a conference" in new WithApplication() {
      val result = await(conferenceRepo.update(Conference("Scala Days", "scaladays@gmail.com", "Scala Days Conference", "April 18th, 2017", "April 21st, 2017", "Chicago", Some(1))))
      result === 1
    }

    "delete a row" in new WithApplication() {
      val result = await(conferenceRepo.delete(1))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}

