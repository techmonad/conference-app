package controllers

import javax.inject._

import play.api.mvc._
import repository.ConferenceRepository

import scala.concurrent.ExecutionContext


@Singleton
class ConferenceController @Inject()(conferenceRepository: ConferenceRepository)(implicit exec: ExecutionContext) extends Controller {

  def index = Action.async {
    //TODO convert into json
    conferenceRepository.getAll.map { msg => Ok(msg.toString()) }
  }


}
