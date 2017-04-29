package repository


import javax.inject.{Inject, Singleton}

import models.Conference
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ConferenceRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends ConferenceTable
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  def create(conference: Conference): Future[Int] = db.run {
    conferenceTableQueryInc += conference
  }

  def createAll(conferences: List[Conference]): Future[Seq[Int]] = db.run {
    conferenceTableQueryInc ++= conferences
  }

  def update(conference: Conference): Future[Int] = db.run {
    conferenceTableQuery.filter(_.id === conference.id).update(conference)
  }

  def getAll(): Future[List[Conference]] = db.run {
    conferenceTableQuery.to[List].result
  }


  def getById(id: Int): Future[Option[Conference]] = db.run {
    conferenceTableQuery.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Int] = db.run {
    conferenceTableQuery.filter(_.id === id).delete
  }

}


private[repository] trait ConferenceTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  lazy protected val conferenceTableQuery = TableQuery[ConferenceTable]

  lazy protected val conferenceTableQueryInc = conferenceTableQuery.returning(conferenceTableQuery.map(_.id))

  class ConferenceTable(tag: Tag) extends Table[Conference](tag, "conference") {

    def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def from: Rep[String] = column[String]("from")

    def to: Rep[String] = column[String]("to")

    def place: Rep[String] = column[String]("place")

    def * = (name, from, to, place, id.?) <> (Conference.tupled, Conference.unapply)
  }

}
