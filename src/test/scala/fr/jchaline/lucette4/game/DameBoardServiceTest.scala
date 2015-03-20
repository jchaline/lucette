package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

import scala.util.Random

@RunWith(classOf[MockitoJUnitRunner])
class DameBoardServiceTest {

  var service = new DameBoardService()

  def setUp(){
    service = new DameBoardService()
  }

  /**
   * Test la recherche de coups simples disponibles
   */
  @Test
  def findMovesTest(){
    val board = new DameBoard()

    val movesBlack = service.findMoves(board, DameBoard.BLACK)
    val movesWhite = service.findMoves(board, DameBoard.WHITE)

    //9 mouvement disponibles au départ du jeu pour chaque joueur
    assertTrue(movesBlack.size==9)
    assertTrue(movesWhite.size==9)

    val secondBoard = board.play(Coord(0,3,1,4)).play(Coord(3,6,2,5))
    val movesWithBlackCatch = service.findMoves(secondBoard, DameBoard.BLACK)

    //assert find mouvement de prise simple
    assertTrue(movesWithBlackCatch.contains(Coord(1,4,3,6)))
  }

  /**
   * Test la recherche de coups complexes disponibles
   */
  @Test
  def findMovesComplexesTest(){
    val board = new DameBoard()

    val secondBoard = board.play(Coord(8,3,7,4)).play(Coord(3,6,2,5))
                      .play(Coord(6,3,7,4)).play(Coord(2,5,1,4))
                      .play(Coord(4,3,5,4)).play(Coord(4,7,3,6))
    val movesWithComplexeCatch = service.findMoves(secondBoard, DameBoard.BLACK)

    //assert find mouvement de prise simple
    assertTrue(movesWithComplexeCatch.contains(Coord(0,3,2,5,2,5,4,7,4,7,6,5)))
  }

  /**
   * Test l'évaluation d'un plateau
   */
  @Test
  def evaluateTest(){
    val board = new DameBoard()
    assertTrue(service.evaluate(board, DameBoard.BLACK)==0)
    assertTrue(service.evaluate(board, DameBoard.WHITE)==0)

    val initCasesBlack = Array(
      Array('_','x','_','x','_','x','_','x','_','x'),
      Array('x','_','x','_','x','_','x','_','x','_'),
      Array('_','x','_','x','_','x','_','x','_','x'),
      Array('_','_','x','_','x','_','x','_','x','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','x','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_')
    )
    val boardBlack = new DameBoard(initCasesBlack, List[DameBoard]())
    assertTrue(service.evaluate(boardBlack, DameBoard.BLACK) == 20)
    assertTrue(service.evaluate(boardBlack, DameBoard.WHITE) == -20)

    val initCasesWhite = Array(
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','o','_','_','o','_','_','_'),
      Array('_','_','o','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','o','_','o','_','o','_','o','_','_'),
      Array('_','_','o','_','o','_','o','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_')
    )
    val boardWhite = new DameBoard(initCasesWhite, List[DameBoard]())
    assertTrue(service.evaluate(boardWhite, DameBoard.BLACK) == -10)
    assertTrue(service.evaluate(boardWhite, DameBoard.WHITE) == 10)

    val initCasesMixes = Array(
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','x','_','o','_','_','o','_','_','_'),
      Array('_','_','o','_','_','x','_','_','_','_'),
      Array('_','_','_','_','o','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','o','_','x','_','o','_','_'),
      Array('x','_','x','_','x','_','o','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_')
    )
    val boardMixe = new DameBoard(initCasesMixes, List[DameBoard]())
    assertTrue(service.evaluate(boardMixe, DameBoard.BLACK) == -1)
    assertTrue(service.evaluate(boardMixe, DameBoard.WHITE) == 1)
  }

  /**
   * Test de jeu automatique avec coup aléatoire, avec maximum de 1000 coups
   */
  @Test
  def recursiveRandomPlayTest(){
    val board = new DameBoard()

    recursirvePlay(board, DameBoard.WHITE, 0)
  }


  /**
   * Joue des coups aléatoire recursivement
   * @param board plateau de jeu
   * @param player joueur courant
   * @param turn numéro du tour actuel
   */
  private def recursirvePlay(board : DameBoard, player:Char, turn:Int):Int ={
    val moves = service.findMoves(board, player)

    if(moves.size>0 && turn<1000){
      recursirvePlay(board.play(moves(Random.nextInt(moves.size))), DameBoard.opponent(player), turn+1)
    }
    else{
      println(turn)
      println(board)
      turn
    }
  }

}
