package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class DameBoardServiceTest {

  /**
   * Test qu'après un mouvement les cases ont bien changé de valeur comme il faut
   */
  @Test
  def moveTestValue(){
    val board = new DameBoard()

    val movesBlack = new DameBoardService().findMoves(board, DameBoard.BLACK)
    val movesWhite = new DameBoardService().findMoves(board, DameBoard.WHITE)

    //9 mouvement disponibles au départ du jeu pour chaque joueur
    assertTrue(movesBlack.size==9)
    assertTrue(movesWhite.size==9)
  }

}
