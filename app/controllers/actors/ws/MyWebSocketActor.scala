package controllers.actors.ws

import akka.actor._
import play.api.mvc._
import de.htwg.mps.portals.controller.{Controller => GameController}
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller._
import de.htwg.mps.portals.util._

object GameActor {
  def props(controller : GameController, out: ActorRef) = Props(new GameActor(controller, out))
}

class GameActor(controller : GameController, out: ActorRef) extends Actor {
  val wui = new Wui(controller)
  
  def receive = {
    case ("left") 		=> wui.moveLeft
    case ("up") 		=> wui.moveUp
    case ("right") 		=> wui.moveRight
    case ("down") 		=> wui.moveDown
    case ("enter")		=> wui.restartLevel
  }
  
  class Wui(val controller: GameController) extends Observer[Event] {
      controller.add(this)
      
      val player = Player.HumanPlayer1
      val level = new Level
      
      def moveDown = controller.moveDown(player)
      def moveUp = controller.moveUp(player)
      def moveLeft = controller.moveLeft(player)
      def moveRight = controller.moveRight(player)
    
      def update(e: Event) = {
	    e match {
	      case _: NewGame  => newGame
	      case x: Update   => move(x.move.player)
	      case _: Wait	   => None
	      case _: GameWon  => gameWon
	      case _: GameLost => gameLost
	    }
	  }
      
      def move(p : Player) = {
        var str = "Update\n"
          str += p.uuid + "\n"
          str += p.position.toString + "\n"
          str += p.nextPosition.toString + "\n"
        status (str)
	  }
      
      def newGame = {
        status ("NewGame\n"+controller.playground.toString)
        controller.playground.player.foreach({ case(k,v) => this.move(v)})
      }
      
	  def gameWon = {
	    level.nextLevel
	    status("GameWon\n")
	  }
	  
	  def gameLost = {
	    status("GameLost\n")
	  }
	
	  // method for restarting a level and going to the nextLevel
	  def restartLevel: Unit = {
	    controller.load(level.currentLevel)
	  }
	
	  // the status of the UI
	  def status(text: String) = out ! (text) 
  }
}