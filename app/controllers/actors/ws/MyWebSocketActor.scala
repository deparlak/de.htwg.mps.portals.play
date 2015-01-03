package controllers.actors.ws

import scala.reflect.io.Path
import akka.actor._
import play.api.mvc._

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  val controller = new controllers.actors.ws.Controller
  def receive = {
    case ("left") 		=> controller.moveLeft("player")
    case ("up") 		=> controller.moveUp("player")
    case ("right") 		=> controller.moveRight("player")
    case ("down") 		=> controller.moveDown("player")
    case (msg : String)	=> out ! ("I received your message: " + msg)
  }
  
  /**
   * Game controller
   */
  // the status of the UI
  def status(text: String) = out ! ("Status : "+text)
  
  var level = Path("res").walkFilter { p => p.isFile }
  var currentLevel = level.next.toString
  
  def gameWon = nextLevel
  def gameLost = status("Game lost. Hit enter to restart this level.")

  // method for restarting a level and going to the nextLevel
  def restartLevel: Unit = {
    controller.load(currentLevel)
    status("")
  }
  def nextLevel: Unit = if (level.hasNext) {
    currentLevel = level.next.toString
    controller.load(currentLevel)
    status("")
  } else {
    level = Path("res") walkFilter { p => p.isFile };
    if (level.hasNext) nextLevel
  }
}