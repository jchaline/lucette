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
    val moves = service.findMoves(board)
    val boardValue = service.evaluate(board)

    moves(Random.nextInt(moves.size))
  }

  /**
   * Implémentation de negamax pour résoudre les meilleurs mouvements
   * @param node noeud actuellement évalué
   * @param depth profondeur maximum de recherche sur le noeud courant
   * @param α paramètre alpha de negamax
   * @param β paramètre beta de negamax
   * @return valeur du noeud
   */
  def negamax(node:DameBoard, depth:Int, α:Int, β:Int):Int={

    //liste des mouvement disponibles sur le noeud courant
    val moves = service.findMoves(node)

    //recherche sur noeuds suivants si possible
    if (depth == 0 || moves.isEmpty ) {
      service.evaluate(node)
    }
    else{
      var αLooped = α
      var bestValue = Int.MinValue

      //évaluation récursive des noeuds suivants
      for(move <- moves){
        val nextNode = node.play(move)

        val evaluation = -negamax(nextNode, depth - 1, -β, -αLooped)
        bestValue = math.max( bestValue, evaluation )
        αLooped = math.max( αLooped, evaluation )

        //TODO:profiter de l'élaguage alpha beta avec arrêt de la boucle

      }
      bestValue
    }
  }

  /**
   * Implémentation de min max
   * @return valeur du noeud courant
   */
  def minmax(node:DameBoard, depth:Int,maximizingPlayer:Boolean,
             heuristic:(DameBoard) => Int,
             children:(DameBoard) => List[DameBoard]):Int= {

    //cas d'arrêt : plus de fils ou limite technique
    if (depth == 0 || children(node).isEmpty) {
      heuristic(node)
    }
    //deux cas, chercher la valeur la plus grande ou la plus faible
    else if (maximizingPlayer) {
      var bestValue = Int.MinValue
      for (child <- children(node)) {
        val value = minmax(child, depth - 1, !maximizingPlayer, heuristic, children)
        bestValue = math.max(bestValue, value)
      }
      bestValue
    }
    else {
      var bestValue = Int.MaxValue
      for (child <- children(node)) {
        val value = minmax(child, depth - 1, !maximizingPlayer, heuristic, children)
        bestValue = math.min(bestValue, value)
      }
      bestValue
    }
  }

  /**
   * Implémentation de min max avec élaguage alpha beta
   * @return
   */
  def alphaBeta():Int={
    0
  }
}
