package controllers

import play.api.Play.current
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import actors.ws.GameActor
import de.htwg.mps.portals.controller.{Controller => GameController}
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.model.Playground._

object Application extends Controller {
  // get a playground instance, to readout the width and height of a playground
  val playground = new Playground
  
  def index = Action {implicit request =>
    Ok(views.html.index(playground.PlaygroundWidth , playground.PlaygroundHeight))
  }
  
  def connect = WebSocket.acceptWithActor[String, String] { request => out =>
    val controller = new GameController
  	val actor = GameActor.props(controller, out)
  	//controller.load()
  	actor
  }
}