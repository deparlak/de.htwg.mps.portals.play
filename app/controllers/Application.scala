package controllers

import play.api.Play.current
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import actors.ws.GameActor
import de.htwg.mps.portals.controller.{Controller => GameController}


object Application extends Controller {
  
  def index = Action {implicit request =>
    Ok(views.html.index())
  }
  
  def connect = WebSocket.acceptWithActor[String, String] { request => out =>
    val controller = new GameController
  	val actor = GameActor.props(controller, out)
  	controller.load()
  	actor
  }
}