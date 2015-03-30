package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, DameBoard}
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class AlphaBetaTest {

  val service = new DameBoardService()
  val solver = new AlphaBeta()

  /**
   * Recherche du meilleur mouvement à jouer
   */
  @Test
  def bestMoveTest(){
    val board = new DameBoard()

    val moves = service.findMoves(board)
    val bestMove = solver.bestMove(board, 3,
      (b:DameBoard) => new DameBoardService().evaluate(b),
      (b:DameBoard) => new DameBoardService().findMoves(b).map{b.play(_)})

    if(!moves.isEmpty){
      assertNotNull(bestMove)
      moves.contains(bestMove)
    }
  }


  /**
   * Test l'implémentation de alpha beta
   */
  @Test
  def alphabetaTest(): Unit ={
    val board = new DameBoard()

    val value = solver.alphabeta(board, 3, true,
      (b:DameBoard) => new DameBoardService().evaluate(b),
      (b:DameBoard) => new DameBoardService().findMoves(b).map{b.play(_)},
      Int.MinValue, Int.MaxValue)

    assertTrue(value == 0)
  }


}
