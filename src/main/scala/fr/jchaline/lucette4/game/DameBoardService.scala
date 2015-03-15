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
   * @param board le plateau pour la recherche de coups
   * @param player le joueur
   * @return la liste des mouvements
   */
  def findMoves(board : DameBoard, player: Char) = {
    var moves = mutable.MutableList[Coord]()

    for (row <- 0 to DIM_X - 1) {
      for (column <- 0 to DIM_Y - 1) {

        //only the turn color can make a move
        if (board.read(column, row).equals(player)) {

          //check what's around the pawn.
          moves ++= checkWard(board, column, row, true, true)
          moves ++= checkWard(board, column, row, true, false)
          moves ++= checkWard(board, column, row, false, false)
          moves ++= checkWard(board, column, row, false, true)
        }
      }
    }
    moves.toList
  }

  /**
   * Check cases autour d'une coordonnée
   * @param board plateau de jeu
   * @param x abscisse du pion a déplacer
   * @param y ordonnée du pion a déplacer
   * @param onwar recherche en avant ou en arrière
   * @param right recherche à droite ou à gauche
   * @return les mouvements possibles pour ce pion
   */
  def checkWard(board:DameBoard, x:Int, y:Int, onwar:Boolean, right:Boolean)={
    var moves = mutable.MutableList[Coord]()
    val deltaX =  if(right) 1 else -1
    val deltaY =  if(onwar) 1 else -1
    val player = board.read(x,y)

    if (board.inBoundary(x + deltaX, y + deltaY)) {
      //si case vide
      if (board.read(x + deltaX, y + deltaY).equals(DameBoard.EMPTY)) {
        //that's a possible move
        moves += Coord(x, y, x + deltaX, y + deltaY)
      }
      //else if it is not player colore, we may take the pawn
      else if (!board.read(x + deltaX, y + deltaY).equals(player)) {
        val tuple = (x + 2*deltaX , y + 2*deltaY)

        //vérification de la case suivante qui doit être vide
        if (board.inBoundary(x + 2*deltaX , y + 2*deltaY)) {
          if (board.read(x + 2*deltaX , y + 2*deltaY).equals(DameBoard.EMPTY)) {
            moves += Coord(x, y, x + 2*deltaX , y + 2*deltaY)
          }
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
