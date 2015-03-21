package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, Coord, DameBoard}


class MiniMax extends Solver {

  val service = new DameBoardService()

  override def bestMove(node:DameBoard, depth:Int,
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
}
