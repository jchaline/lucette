package fr.jchaline.lucette4.game

import scala.collection.mutable

/**
 * Service permettant de travailler sur le plateau du jeu de Dame
 */
class DameBoardService {

  def DIM_X = 10
  def DIM_Y = 10

  /**
   * Détermine la liste de mouvements/coups possible pour un joueur sur un plateau
   * @param board le plateau pour la recherche de coups
   * @param player le joueur
   * @return la liste des mouvements
   */
  def findMoves(board:DameBoard, player:Char)={
    moveAvailables(board,player)
  }

  def inBoundary(x: Int, y: Int) = (x < DIM_X && x >= 0 && y < DIM_Y && y >= 0)

  def caseMatchValue(board : DameBoard, x: Int, y: Int, value: Char) = {
    board.read(x, y).equals(value)
  }

  def moveAvailables(board : DameBoard, player: Char) = {
    var moves = mutable.MutableList[Coord]()

    for (row <- 0 to DIM_X - 1) {
      for (column <- 0 to DIM_Y - 1) {

        //only the turn color can make a move
        if (caseMatchValue(board, column, row, player)) {
          //check what's around the pawn.
          //1) check onward left
          if (inBoundary(column - 1, row + 1)) {
            if (caseMatchValue(board, column - 1,row + 1,  DameBoard.EMPTY)) {
              //that's a possible move
              moves += Coord(column, row, column - 1, row + 1)
            } else //if the color is different we may take the pawn
            if (!caseMatchValue(board, column - 1, row + 1, player)) {
              if (inBoundary( column - 2, row + (2 * 1))) {
                if (caseMatchValue(board, column - 2, row + (2 * 1), DameBoard.EMPTY)) {
                  moves += Coord(column, row, column - 2, row + (2 * 1))
                }
              }
            }
          }

          //2) check onward right
          if (inBoundary(row + 1, column + 1)) {
            if (caseMatchValue(board, column + 1, row + 1, DameBoard.EMPTY)) {
              //that's a possible move
              moves += Coord(column, row, column + 1, row + 1)
            } //if the color is different we may take the pawn
            else {
              if (!caseMatchValue(board, column + 1, row + 1, player)) {
                if (inBoundary(column + 2, row + (2 * 1))) {
                  if (caseMatchValue(board, column + 2, row + (2 * 1), DameBoard.EMPTY)) {
                    moves += Coord( column, row, column + 2, row + (2 * 1))
                  }
                }
              }
            }
          }

          //3) check backward left only if we take the pawn
          if (inBoundary(row - 1, column - 1)) {
            if (!caseMatchValue(board,  column - 1, row - 1, player)
              && !caseMatchValue(board, column - 1, row - 1, DameBoard.EMPTY)) {
              if (inBoundary(row - (2 * 1), column - 2)) {
                if (caseMatchValue(board,  column - 2, row - (2 * 1),DameBoard.EMPTY)) {
                  moves += Coord(column, row, column - 2, row - (2 * 1))
                }
              }
            }
          }

          //4) check backward right only if we take the pawn
          if (inBoundary(row - 1, column + 1)) {
            if (!caseMatchValue(board, column + 1, row - 1, player)
              && !caseMatchValue(board, column + 1, row - 1, DameBoard.EMPTY)) {
              if (inBoundary(row - (2 * 1), column + 2)) {
                if (caseMatchValue(board, column + 2, row - (2 * 1), DameBoard.EMPTY)) {
                  moves += Coord( column, row, column + 2, row - (2 * 1))
                }
              }
            }
          }
        }
      }
    }
    moves.toList
  }

}
