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
   * Implémentation de negamax pour résoudre les meilleurs mouvements
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

  /**
   * Implémentation de min max
   * @return valeur du noeud courant
   */
  def minmax(node:DameBoard, depth:Int,maximizingPlayer:Boolean, player:Char,
             heuristic:(DameBoard, Char) => Int,
             children:(DameBoard, Char) => List[DameBoard]):Int= {

    //détermination du joueur adverse
    val otherPlayer = player match{
      case DameBoard.BLACK => DameBoard.WHITE
      case DameBoard.WHITE => DameBoard.BLACK
    }

    //cas d'arrêt : plus de fils ou limite technique
    if (depth == 0 || children(node, otherPlayer).isEmpty) {
      heuristic(node, player)
    }
    //deux cas, chercher la valeur la plus grande ou la plus faible
    //TODO:bug car doublon entre le role de maximizing et l'alternance du joueur
    //problème : besoin de savoir qui joue pour avoir sa liste de coup possible
    //solution : est-ce qu'il faut faire porter le tour du joueur par le plateau ?
    //ainsi la recherche de coup et l'évaluation n'auront qu'un parametre, le plateau
    //cohérent dans la plupart des jeux de plateaux ?
    else if (maximizingPlayer) {
      var bestValue = Int.MinValue
      for (child <- children(node, otherPlayer)) {
        val value = minmax(child, depth - 1, !maximizingPlayer, otherPlayer, heuristic, children)
        bestValue = math.max(bestValue, value)
      }
      bestValue
    }
    else {
      var bestValue = Int.MaxValue
      for (child <- children(node, otherPlayer)) {
        val value = minmax(child, depth - 1, !maximizingPlayer, otherPlayer, heuristic, children)
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
