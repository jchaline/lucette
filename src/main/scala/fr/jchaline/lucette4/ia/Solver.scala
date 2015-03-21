package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{Coord, DameBoard}

trait Solver {

  def bestMove(node:DameBoard, depth:Int,
               heuristic:(DameBoard) => Int,
               childrens:(DameBoard) => List[DameBoard]):Coord
}
