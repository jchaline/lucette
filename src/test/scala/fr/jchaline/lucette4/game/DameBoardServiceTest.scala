package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class DameBoardServiceTest {

  /**
   * Test la recherche de coups disponibles
   */
  @Test
  def findMovesTest(){
    val board = new DameBoard()

    val movesBlack = new DameBoardService().findMoves(board, DameBoard.BLACK)
    val movesWhite = new DameBoardService().findMoves(board, DameBoard.WHITE)

    //9 mouvement disponibles au départ du jeu pour chaque joueur
    assertTrue(movesBlack.size==9)
    assertTrue(movesWhite.size==9)

    val secondBoard = board.move(Coord(0,3,1,4)).move(Coord(3,6,2,5))
    val movesWithBlackCatch = new DameBoardService().findMoves(secondBoard, DameBoard.BLACK)

    //assert find mouvement de prise simple
    assertTrue(movesWithBlackCatch.contains(Coord(1,4,3,6)))
  }

}
