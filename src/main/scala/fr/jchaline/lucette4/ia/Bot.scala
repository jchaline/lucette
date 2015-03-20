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
    val boardValue = service.evaluate(board, player)

    moves(Random.nextInt(moves.size))
  }

  /**
   * Implémentation de negamax
   * @param node noeud actuellement évalué
   * @param depth profondeur maximum de recherche sur le noeud courant
   * @param α paramètre alpha de negamax
   * @param β paramètre beta de negamax
   * @param player joueur concerné par l'évaluation du noeud
   * @return valeur du noeud
   */
  def solve(node:DameBoard, depth:Int, α:Int, β:Int, player:Char):Int={
    //Détermination de l'adversaire du joueur
    val otherPlayer = player match{
      case DameBoard.BLACK => DameBoard.WHITE
      case DameBoard.WHITE => DameBoard.BLACK
    }

    //liste des mouvement disponibles sur le noeud courant
    val moves = service.findMoves(node, otherPlayer)

    //recherche sur noeuds suivants si possible
    if (depth == 0 || moves.isEmpty ) {
      service.evaluate(node, player)
    }
    else{
      var αLooped = α
      var bestValue = Int.MinValue

      //évaluation récursive des noeuds suivants
      for(move <- moves){
        val nextNode = node.play(move)

        val evaluation = -solve(nextNode, depth - 1, -β, -αLooped, otherPlayer)
        bestValue = math.max( bestValue, evaluation )
        αLooped = math.max( αLooped, evaluation )

        //TODO:profiter de l'élaguage alpha beta avec arrêt de la boucle

      }
      bestValue
    }
  }
}
