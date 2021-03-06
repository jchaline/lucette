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
   * @return la liste des mouvements
   */
  def findMoves(board : DameBoard, onlyCatch:Boolean=false):List[Coord] = {
    var moves = mutable.MutableList[Coord]()

    for (row <- 0 to DIM_X - 1) {
      for (column <- 0 to DIM_Y - 1) {

        val coord = Coord(column, row)
        //only the turn color can make a move
        if (board.read(coord).equals(board._player)) {

          //check what's around the pawn.
          moves ++= checkAllMoves(board, coord, onlyCatch)
        }
      }
    }
    moves.toList
  }

  /**
   * Recherche de mouvement autour d'un point
   * @param board plateau de jeu
   * @param coord coordonnées du pion a déplacer
   * @param onlyCatch recherche seulement les prises ou également les déplacements simple
   * @return les mouvements possibles pour ce pion
   */
  def checkAllMoves(board : DameBoard, coord:Coord, onlyCatch:Boolean):List[Coord]={
    checkMoves(board, coord, true, true, onlyCatch) ++
    checkMoves(board, coord, true, false, onlyCatch) ++
    checkMoves(board, coord, false, false, onlyCatch) ++
    checkMoves(board, coord, false, true, onlyCatch)
  }

  /**
   * Check cases autour d'une coordonnée,
   * TODO: transformer en recursif pour trouver toutes les combinaisons de prise multiple,
   * en utilisant findMoves et checkMoves avec param pour prise uniquement (pas déplacement simple)
   * @param board plateau de jeu
   * @param coord coordonnées du pion a déplacer
   * @param onwar recherche en avant ou en arrière
   * @param right recherche à droite ou à gauche
   * @param onlyCatch recherche seulement les prises ou également les déplacements simple
   * @return les mouvements possibles pour ce pion
   */
  def checkMoves(board:DameBoard, coord:Coord, onwar:Boolean, right:Boolean, onlyCatch:Boolean):List[Coord]={
    val emptyList = List[Coord]()
    val deltaX =  if(right) 1 else -1
    val deltaY =  if(onwar) 1 else -1

    //case à proximité, parmis les différent sens accessible
    val nextWard = Coord(coord.get(0) + deltaX, coord.get(1) + deltaY)

    //si la case est bien sur le plateau
    if (board.inBoundary(nextWard)) {
      //si case vide, mouvement possible
      if (board.read(nextWard).equals(DameBoard.EMPTY)) {
        if(!onlyCatch){
          val move = coord++nextWard
          List(move)
        }
        else{
          List[Coord]()
        }
      }
      //sinon, si case adverse, vérification de la case plus loin
        //recherche recursive pour trouver les différents chemins
      else if (!board.read(nextWard).equals(board._player)) {

        //cible permettant une prise
        val nextCatch = Coord(coord.get(0) + 2*deltaX , coord.get(1) + 2*deltaY)

        //vérification de la case suivante qui doit être vide
        if (board.inBoundary(nextCatch) && board.read(nextCatch).equals(DameBoard.EMPTY)) {
          val move = coord++nextCatch

          //à partir de là, recherche des autres mouvement de prise disponibles, recursivité
          val otherCatchList = checkAllMoves(board.play(move, true), nextCatch, true)

          //si d'autres prises possibles, concatenations
          if(otherCatchList.size>0){
            otherCatchList.map(move++_)
          }
          //sinon, enregistrement de la prise
          else{
            List(move)
          }
        }
        else{
          emptyList
        }
      }
      else{
        emptyList
      }
    }
    else{
      emptyList
    }
  }

  /**
   * Evalue la valeur d'un plateau
   * Plus la valeur est elevé, plus elle avantage le joueur BLANC
   * Plus la valeur est faible, plus elle avantage le joueur NOIR
   * @param board plateau à évaluer
   * @return la valeur
   */
  def evaluate(board:DameBoard)={
    var value = 0
    //analyse des lignes
    for (x <- 0 to DIM_X - 1) {
      //analyse des colonnes de chaque ligne
      for (y <- 0 to DIM_Y - 1) {
        value = value + evaluateWard(board, x, y)
      }
    }
    value
  }

  /**
   * Evaluation d'une case du plateau
   * @param board plateau de jeu
   * @param x abscisse de la case
   * @param y ordonnée de la case
   * @return la valeur de la case sur ce plateau
   */
  def evaluateWard(board:DameBoard, x:Int, y:Int)={
    board.read(x,y) match {
      case DameBoard.WHITE => 1
      case DameBoard.BLACK => -1
      case _ => 0
    }
  }
}
