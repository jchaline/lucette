package fr.jchaline.lucette4.ia

import fr.jchaline.lucette4.game.{DameBoardService, DameBoard}
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class BotTest {

  @Test
  def bestMoveTest(){
    val board = new DameBoard()
    val player = DameBoard.BLACK

    val bestMove = new Bot().bestMove(board, player, Int.MaxValue, Int.MinValue)
    assertNotNull(bestMove)
  }

}
