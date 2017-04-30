package controllers

import javax.inject._

import models.Conference
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repository.ConferenceRepository

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ConferenceController @Inject()
(
  conferenceRepository: ConferenceRepository
)(implicit exec: ExecutionContext) extends Controller {

  def index = Action.async { implicit request =>
    //TODO convert into json
    conferenceRepository.getAll.map { conferences =>
      Ok(views.html.index("Conferences : Home", conferences))
    }
  }

  val conferenceForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "description" -> nonEmptyText,
      "from" -> nonEmptyText,
      "to" -> nonEmptyText,
      "place" -> nonEmptyText,
      "id" -> optional(number)
    )(Conference.apply)(Conference.unapply)
  )

  def conferenceSubmit = Action.async { implicit request =>
    conferenceForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        Future.successful(Redirect(routes.ConferenceController.index()))
      },
      conferenceData => {
        conferenceRepository.create(conferenceData).map(_ => Redirect(routes.ConferenceController.index()))
      }
    )
  }

}
