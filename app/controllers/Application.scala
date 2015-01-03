package controllers

import play.api.Play.current
import akka.actor.ActorSelection.toScala
import akka.actor.PoisonPill
import play.api.libs.iteratee.Concurrent
import play.api.libs.iteratee.Iteratee
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import play.libs.Akka
import scala.concurrent.ExecutionContext.Implicits.global
import actors.ws.WebSocketChannel
import actors.ws.MyWebSocketActor
import actors.ws.Game
import controllers.actors.ws.Move

object Application extends Controller {
  
  def index = Action {implicit request =>
    Ok(views.html.index())
  }
  
  def connect = WebSocket.acceptWithActor[String, String] { request => out =>
  	MyWebSocketActor.props(out)
  }
}