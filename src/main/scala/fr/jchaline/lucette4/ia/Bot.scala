package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, Coord, DameBoard}

import scala.util.Random

class Bot {

  val service = new DameBoardService()

  /**
   * Détermine quel est le meilleur mouvement parmi ceux disponibles,
   * à l'aide de l'algorithme negamax
   * @param node plateau à évaluer
   * @return le meilleur mouvement
   */
  def bestMove(node:DameBoard, depth:Int,
               heuristic:(DameBoard) => Int,
               childrens:(DameBoard) => List[DameBoard]):Coord={

    service.findMoves(node)
      .map{move =>
        move -> minimax(
            node.play(move),depth,
            !DameBoard.WHITE.equals(node._player),
            heuristic, childrens)
        }
      .sortWith(_._2 > _._2)
      .last._1
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
   * Implémentation de minimax
   * @param node noeud courant évalué
   * @param depth profondeur maximum d'évaluation
   * @param heuristic fonction d'évaluation d'un noeud
   * @param childrens fonction permettant d'obtenir la liste des noeuds fils
   * @return valeur du noeud courant
   */
  def minimax(node:DameBoard, depth:Int,maximizingScore:Boolean,
             heuristic:(DameBoard) => Int,
             childrens:(DameBoard) => List[DameBoard]):Int= {

    //cas d'arrêt : plus de fils ou limite technique
    if (depth == 0 || childrens(node).isEmpty) {
      heuristic(node)
    }
    //deux cas, chercher la valeur la plus grande ou la plus faible
    else if (maximizingScore) {
      var bestValue = Int.MinValue
      for (child <- childrens(node)) {
        val value = minimax(child, depth - 1, !maximizingScore, heuristic, childrens)
        bestValue = math.max(bestValue, value)
      }
      bestValue
    }
    else {
      var bestValue = Int.MaxValue
      for (child <- childrens(node)) {
        val value = minimax(child, depth - 1, !maximizingScore, heuristic, childrens)
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
