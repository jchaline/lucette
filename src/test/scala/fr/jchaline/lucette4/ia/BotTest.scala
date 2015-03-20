package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, DameBoard}
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class BotTest {

  val service = new DameBoardService()
  val alicia = new Bot()

  @Before
  def setUp(){
  }

  /**
   * Recherche du meilleur mouvement à jouer
   */
  @Test
  def bestMoveTest(){
    val board = new DameBoard()
    val player = DameBoard.BLACK

    val alicia = new Bot()
    val moves = service.findMoves(board)
    val bestMove = alicia.bestMove(board, 3,
      (b:DameBoard) => new DameBoardService().evaluate(b),
      (b:DameBoard) => new DameBoardService().findMoves(b).map{b.play(_)})

    if(!moves.isEmpty){
      assertNotNull(bestMove)
      moves.contains(bestMove)
    }
  }

  /**
   * Evaluation d'un plateau en tant que noeud et en fonction de ses sous noeuds via negamax
   */
  @Test
  def negamaxTest(){
    val board = new DameBoard()

    val player = DameBoard.BLACK

    //compute value with negamax
    val value = alicia.negamax(board, 3, Int.MaxValue, Int.MinValue)

    assertTrue(value == 0)
  }

  /**
   * Test l'implémentation de minimax
   */
  @Test
  def minimaxTest(): Unit ={
    val board = new DameBoard()
    val player = DameBoard.BLACK

    val value = alicia.minimax(board, 3, true,
      (b:DameBoard) => new DameBoardService().evaluate(b),
      (b:DameBoard) => new DameBoardService().findMoves(b).map{b.play(_)})

    assertTrue(value == 0)
  }
}
