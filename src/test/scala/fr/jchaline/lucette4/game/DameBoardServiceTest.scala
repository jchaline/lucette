package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

import scala.util.Random

@RunWith(classOf[MockitoJUnitRunner])
class DameBoardServiceTest {

  val service = new DameBoardService()

  /**
   * Test la recherche de coups disponibles
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
   * Test de jeu automatique avec coup aléatoire, avec maximum de 10 coups
   */
  @Test
  def recursivePlayTest(){
    val board = new DameBoard()

    recursirvePlay(board, true, 0)
  }


  private def recursirvePlay(board : DameBoard, black:Boolean, turn:Int): Unit ={
    val player = if(black) DameBoard.BLACK else DameBoard.WHITE
    val moves = service.findMoves(board, player)
    if(moves.size>0 && turn<10){
      recursirvePlay(board.play(moves(Random.nextInt(moves.size) )), !black, turn+1)
    }
    else{
      turn
    }
  }

}
