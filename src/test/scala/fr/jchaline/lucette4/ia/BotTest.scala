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
    val moves = service.findMoves(board,player)
    val bestMove = alicia.bestMove(board, player, Int.MaxValue, Int.MinValue)
    if(!moves.isEmpty){
      assertNotNull(bestMove)
      moves.contains(bestMove)
    }
  }

  /**
   * Evaluation d'un plateau en tant que noeud et en fonction de ses sous noeuds via negamax
   */
  @Test
  def negaMaxTest(){
    val board = new DameBoard()

    val player = DameBoard.BLACK

    //compute value with negamax
    val value = alicia.solve(board, 10, Int.MaxValue, Int.MinValue, player)

    assertTrue(value == 0)
  }

}
