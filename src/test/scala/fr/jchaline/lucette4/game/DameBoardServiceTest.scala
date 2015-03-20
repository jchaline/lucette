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

    val movesBlack = service.findMoves(board)
    val movesWhite = service.findMoves(board)

    //9 mouvement disponibles au départ du jeu pour chaque joueur
    assertTrue(movesBlack.size==9)
    assertTrue(movesWhite.size==9)

    val secondBoard = board.play(Coord(3,3,2,4)).play(Coord(0,6,1,5))
    val movesWithBlackCatch = service.findMoves(secondBoard)

    //assert find mouvement de prise simple
    assertTrue(movesWithBlackCatch.contains(Coord(2,4,0,6)))
  }

  /**
   * Test la recherche de coups complexes disponibles
   */
  @Test
  def findMovesComplexesTest(){
    val initCases = Array(
      Array('_','x','_','x','_','x','_','x','_','x'),
      Array('x','_','x','_','x','_','x','_','x','_'),
      Array('_','x','_','x','_','x','_','x','_','x'),
      Array('x','_','x','_','x','_','x','_','x','_'),
      Array('_','o','_','_','_','_','_','_','_','_'),
      Array('_','_','_','_','_','_','_','_','_','_'),
      Array('_','o','_','o','_','o','_','o','_','o'),
      Array('o','_','o','_','_','_','o','_','o','_'),
      Array('_','o','_','o','_','o','_','o','_','o'),
      Array('o','_','o','_','o','_','o','_','o','_')
    )

    val board = new DameBoard(initCases, DameBoard.BLACK)

    val movesWithComplexeCatch = service.findMoves(board)

    //assert find mouvement de prise simple
    assertTrue(movesWithComplexeCatch.contains(Coord(0,6,2,4,2,4,4,2,4,2,6,4)))
  }

  /**
   * Test l'évaluation d'un plateau
   */
  @Test
  def evaluateTest(){
    val board = new DameBoard()
    assertTrue(service.evaluate(board)==0)

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
    val boardBlack = new DameBoard(initCasesBlack)
    assertTrue(service.evaluate(boardBlack) == -20)

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
    val boardWhite = new DameBoard(initCasesWhite)
    assertTrue(service.evaluate(boardWhite) == 10)

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
    val boardMixe = new DameBoard(initCasesMixes)
    assertTrue(service.evaluate(boardMixe) == 1)
  }

  /**
   * Test de jeu automatique avec coup aléatoire, avec maximum de 1000 coups
   */
  @Test
  def recursiveRandomPlayTest(){
    val board = new DameBoard()

    recursirvePlay(board, 0)
  }


  /**
   * Joue des coups aléatoire recursivement
   * @param board plateau de jeu
   * @param turn numéro du tour actuel
   */
  private def recursirvePlay(board : DameBoard, turn:Int):Int ={
    val moves = service.findMoves(board)

    if(moves.size>0 && turn<1000){
      recursirvePlay(board.play(moves(Random.nextInt(moves.size))), turn+1)
    }
    else{
      println(turn)
      println(board)
      turn
    }
  }

}
