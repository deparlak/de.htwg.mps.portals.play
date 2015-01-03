package controllers.actors.ws

import akka.actor._
import play.api.mvc._

object GameActor {
  def props(out: ActorRef) = Props(new GameActor(out))
}

class GameActor(out: ActorRef) extends Actor {
  val controller = new controllers.actors.ws.Controller
  def receive = {
    case ("left") 		=> controller.moveLeft("player")
    case ("up") 		=> controller.moveUp("player")
    case ("right") 		=> controller.moveRight("player")
    case ("down") 		=> controller.moveDown("player")
    case (msg : String)	=> out ! ("I received your message: " + msg)
  }
}