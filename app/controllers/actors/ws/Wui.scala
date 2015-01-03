package controllers.actors.ws

import play.libs.Akka
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.reflect.io.Path
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.ActorRef

class Wui(val controller: Controller, out: ActorRef) {
  
  
  /**
   * Game controller observer.
   */
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

  // the status of the UI
  def status(text: String) = out ! ("Status : " + text)
}

