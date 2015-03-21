package fr.jchaline.lucette4.engine

import java.util.UUID


/**
 * Moteur de jeu, gère la création de partie, et les intéractions entre les différents joueurs
 */
class Engine {

  val games : scala.collection.mutable.Map[String,Game] = scala.collection.mutable.Map()
  val bots = Map(1 -> "Donald Duck", 2 -> "Moyen", 3 -> "Chuck Norris")

  /**
   * Creation de jeu contre des bots
   * @param idPlayer
   */
  def createGame(idPlayer:String, level:Int)={
    val idGame = UUID.randomUUID().toString
    //assure un level entre 1 et 3
    val idBot = bots(math.max(1,math.min(3,level)))

    games.put(idGame, new Game(idGame, idPlayer, idBot))
  }

}
