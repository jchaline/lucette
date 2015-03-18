package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, Coord, DameBoard}

import scala.util.Random

class Bot {

  val service = new DameBoardService()

  /**
   * Détermine quel est le meilleur mouvement parmi ceux disponibles,
   * à l'aide de l'algorithme negamax
   * @param board plateau à évaluer
   * @param player joueur concerné par l'évaluation
   * @param alpha paramètre alpha, utile à l'évaluation negamax
   * @param beta paramètre beta, utile à l'évaluation negamax
   * @return le meilleur mouvement
   */
  def bestMove(board:DameBoard, player:Char, alpha:Int, beta:Int):Coord={
    val moves = service.findMoves(board, player)
    val boardValue = service.evaluate(board)

    moves(Random.nextInt(moves.size))
  }

}
