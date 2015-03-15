package fr.jchaline.lucette4.game

import scala.collection.mutable

/**
 * Service permettant de travailler sur le plateau du jeu de Dame
 */
class DameBoardService {

  //dimenssion du plateau
  val DIM_X = 10
  val DIM_Y = 10

  def caseMatchValue(board : DameBoard, x: Int, y: Int, value: Char) = {
    board.read(x, y).equals(value)
  }


  /**
   * Détermine la liste de mouvements/coups possible pour un joueur sur un plateau
   * La plupart des coups comportent 1 tuple de coordonnées x,y
   * mais les prises multiples seront de la forme (x1,y1),(x2,y2),...,(xn,yn)
   * @param board le plateau pour la recherche de coups
   * @param player le joueur
   * @return la liste des mouvements
   */
  def findMoves(board : DameBoard, player: Char) = {
    var moves = mutable.MutableList[Coord]()

    for (row <- 0 to DIM_X - 1) {
      for (column <- 0 to DIM_Y - 1) {

        val coord = Coord(column, row)
        //only the turn color can make a move
        if (board.read(coord).equals(player)) {

          //check what's around the pawn.
          moves ++= checkMoves(board, coord, true, true)
          moves ++= checkMoves(board, coord, true, false)
          moves ++= checkMoves(board, coord, false, false)
          moves ++= checkMoves(board, coord, false, true)
        }
      }
    }
    moves.toList
  }

  /**
   * Check cases autour d'une coordonnée,
   * TODO: transformer en recursif pour trouver toutes les combinaisons de prise multiple,
   * en utilisant findMoves et checkMoves avec param pour prise uniquement (pas déplacement simple)
   * @param board plateau de jeu
   * @param coord coordonnées du pion a déplacer
   * @param onwar recherche en avant ou en arrière
   * @param right recherche à droite ou à gauche
   * @return les mouvements possibles pour ce pion
   */
  def checkMoves(board:DameBoard, coord:Coord, onwar:Boolean, right:Boolean)={
    var moves = mutable.MutableList[Coord]()
    val deltaX =  if(right) 1 else -1
    val deltaY =  if(onwar) 1 else -1
    val player = board.read(coord)

    //case à proximité, parmis les différent sens accessible
    val nextWard = Coord(coord.get(0) + deltaX, coord.get(1) + deltaY)

    //si la case est bien sur le plateau
    if (board.inBoundary(nextWard)) {

      //si case vide, mouvement possible
      if (board.read(nextWard).equals(DameBoard.EMPTY)) {
        moves += coord++nextWard
      }
      //sinon, si case adverse, vérification de la case plus loin
        //recherche recursive pour trouver les différents chemins
      else if (!board.read(nextWard).equals(player)) {

        //cible permettant une prise
        val nextCatch = Coord(coord.get(0) + 2*deltaX , coord.get(1) + 2*deltaY)

        //vérification de la case suivante qui doit être vide
        if (board.inBoundary(nextCatch) && board.read(nextCatch).equals(DameBoard.EMPTY)) {
          moves += coord++nextCatch
        }
      }
    }
    moves
  }

  /**
   * Parse une chaine de caractère et retourne l'objet DameBoard correspondant
   * @param board plateau sous forme de chaine
   * @return l'objet D
   */
  def fromString(board:String)={
    new DameBoard()
  }

}
