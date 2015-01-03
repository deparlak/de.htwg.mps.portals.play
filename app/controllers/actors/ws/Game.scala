package controllers.actors.ws

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.reflect.io.Path
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props

object GameConfig {
  val marchTimeSeconds = 1
}

object Restart
case class Move(player : String, direction : String)

object Game {
  def props(uuid: String, out: ActorRef): Props = Props(new Game(uuid, out))
}

class Game(name: String, out: ActorRef) extends Actor {
  /**
   * Actor specific code.
   */
  // var march = context.system.scheduler.schedule(0 seconds, GameConfig.marchTimeSeconds.seconds, self, Right)
  
  def receive = {
    case Restart => {
      restartLevel
      //webSocketChannel ! Send("send example")
    }
    case Move(player, "left") => {
      println("Receiver")
      controller.moveLeft(player);
    }
    case Move(player, "up") => {
      controller.moveUp(player);
    }
    case Move(player, "right") => {
      controller.moveRight(player);
    }
    case Move(player, "down") => {
      controller.moveDown(player);
    }
  }
  
  /**
   * Game controller observer.
   */
  var level = Path("res").walkFilter { p => p.isFile }
  var currentLevel = level.next.toString
  val controller = new Controller()
  
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

  // the status of the UI
  def status(text: String) = println(text)
}
