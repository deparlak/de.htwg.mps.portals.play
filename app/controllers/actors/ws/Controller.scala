package controllers.actors.ws

class Controller {
  def moveUp(uuid : String) = println("up " + uuid)
  def moveDown(uuid : String) = println("down " + uuid)
  def moveLeft(uuid : String) = println("left " + uuid)
  def moveRight(uuid : String) = println("right " + uuid)
  def load(file : String) = println("load "+file)
}