package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, Coord, DameBoard}

class AlphaBeta extends Solver {

  val service = new DameBoardService()

  override def bestMove(node:DameBoard, depth:Int,
               heuristic:(DameBoard) => Int,
               childrens:(DameBoard) => List[DameBoard]):Coord={

    service.findMoves(node)
      .map{move =>
        move -> alphabeta(
          node.play(move),depth,
          !DameBoard.WHITE.equals(node._player),
          heuristic, childrens,
          Int.MinValue, Int.MaxValue)
      }
      .sortWith(_._2 > _._2)
      .last._1
  }

  /**
   * Implémentation de minimax avec élaguage alpha beta
   * @param node noeud courant évalué
   * @param depth profondeur maximum d'évaluation
   * @param heuristic fonction d'évaluation d'un noeud
   * @param childrens fonction permettant d'obtenir la liste des noeuds fils
   * @param α paramètre alpha
   * @param β paramètre beta
   * @return valeur du noeud courant
   */
  def alphabeta(node:DameBoard, depth:Int,maximizingScore:Boolean,
                heuristic:(DameBoard) => Int,
                childrens:(DameBoard) => List[DameBoard],
                α:Int, β:Int):Int= {

    //cas d'arrêt : plus de fils ou limite technique
    if (depth == 0 || childrens(node).isEmpty) {
      heuristic(node)
    }
    else{
      var result = 0
      if(!maximizingScore){
        result = Int.MaxValue
        var βNew = β
        childrens(node).foreach { child =>
          result = math.min(result, alphabeta(child, depth - 1, !maximizingScore, heuristic, childrens, α, βNew))
          if (α >= result) {
            return result
          }
          βNew = math.min(βNew, result)
        }
      }
      else{
        result = Int.MinValue
        var αNew = α
        childrens(node).foreach { child =>
          result = math.min(result, alphabeta(child, depth - 1, !maximizingScore, heuristic, childrens, αNew, β))
          if (result >= β) {
            return result
          }
          αNew = math.max(αNew, result)
        }
      }
      result
    }
  }

}
