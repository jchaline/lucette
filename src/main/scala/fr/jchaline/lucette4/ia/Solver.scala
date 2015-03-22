package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{Coord, DameBoard}

/**
 * Interface pour les différents algorithmes de résolution du meilleur mouvement
 */
trait Solver {

  /**
   * Point d'entrée de la recherche du meilleur mouvement
   * @param node plateau à évaluer
   * @param depth profondeur, permet de limiter la recherche en temps/resources
   * @param heuristic fonction d'évaluation d'un noeud
   * @param childrens fonction permettant d'obtenir les noeuds suivants
   * @return le meilleur mouvement présumé
   */
  def bestMove(node:DameBoard, depth:Int,
               heuristic:(DameBoard) => Int,
               childrens:(DameBoard) => List[DameBoard]):Coord
}
