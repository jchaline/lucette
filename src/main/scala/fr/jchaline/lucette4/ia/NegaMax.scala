package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, Coord, DameBoard}

/**
 * Implémentation de alpha beta avec simplification negamax
 */
class NegaMax extends Solver{

  val service = new DameBoardService()

  override def bestMove(node:DameBoard, depth:Int,
               heuristic:(DameBoard) => Int,
               childrens:(DameBoard) => List[DameBoard]):Coord={

    service.findMoves(node)
      .map{move =>
      move -> negamax(
        node.play(move),depth,
        !DameBoard.WHITE.equals(node._player),
        heuristic, childrens,
        Int.MinValue, Int.MaxValue)
    }
      .sortWith(_._2 > _._2)
      .last._1
  }


  /**
   * Implémentation de negamax
   * TODO: implémenter
   * @param node noeud courant évalué
   * @param depth profondeur maximum d'évaluation
   * @param heuristic fonction d'évaluation d'un noeud
   * @param childrens fonction permettant d'obtenir la liste des noeuds fils
   * @param α paramètre alpha
   * @param β paramètre beta
   * @return valeur du noeud courant
   */
  def negamax(node:DameBoard, depth:Int,maximizingScore:Boolean,
              heuristic:(DameBoard) => Int,
              childrens:(DameBoard) => List[DameBoard],
              α:Int, β:Int):Int={
    0
  }

}
