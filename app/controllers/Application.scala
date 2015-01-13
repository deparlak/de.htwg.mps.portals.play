package controllers

import play.api.Play.current
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import actors.ws.GameActor
import de.htwg.mps.portals.controller.{Controller => GameController}
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.model.Playground._


import com.escalatesoft.subcut.inject.AutoInjectable
import de.htwg.mps.portals.config.TestConfiguration
import de.htwg.mps.portals.controller.IController
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.view._
import de.htwg.mps.portals.actor.AktorSystem

object Application extends Controller with AutoInjectable {
  // get a playground instance, to readout the width and height of a playground
  val playground = new Playground
  
  implicit val bindingModule = TestConfiguration
  
  
  def index = Action {implicit request =>
    Ok(views.html.index(playground.PlaygroundWidth , playground.PlaygroundHeight))
  }
  
  def connect = WebSocket.acceptWithActor[String, String] { request => out =>
    
    val controller = inject[IController]
    val actorSystem = inject[AktorSystem]
    
  	val gameActor = GameActor.props(controller, out)
  	gameActor
  }
}