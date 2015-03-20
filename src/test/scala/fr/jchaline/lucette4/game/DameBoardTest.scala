package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class DameBoardTest {

  /**
   * Test qu'après un mouvement les cases ont bien changé de valeur comme il faut
   */
  @Test
  def playSimpleMove(){
    val board = new DameBoard()

    //assert value before mouvement
    assertTrue(board.read(1,3).equals(DameBoard.WHITE))
    assertTrue(board.read(2,4).equals(DameBoard.EMPTY))

    val moved = board.play(Coord(1,3,2,4))

    //assert immutabilité
    assertTrue(board.read(1,3).equals(DameBoard.WHITE))
    assertTrue(board.read(2,4).equals(DameBoard.EMPTY))

    //assert mouvement sur nouveau plateau
    assertTrue(moved.read(1,3).equals(DameBoard.EMPTY))
    assertTrue(moved.read(2,4).equals(DameBoard.WHITE))

    //assert que le nouveau plateau n'est pas le même
    assertFalse( moved.equals( board ) )
  }

  /**
   * Test d'un déplacement complexe, multiples prises
   */
  @Test
  def playComplexeCatch(){
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
    val secondBoard = board.play(Coord(0,6,2,3,2,3,4,2,4,2,6,4))

    assertTrue(secondBoard.read(6,4).equals(DameBoard.BLACK))
    assertTrue(secondBoard.read(1,4).equals(DameBoard.EMPTY))
    assertTrue(secondBoard.read(3,6).equals(DameBoard.EMPTY))
  }



  /**
   * Test une suite de coups et valide le resultat final
   * Déplacements et prise simple
   */
  @Test
  def playMultipleMove(){

    //init du plateau et déplacement de pions pour permettre une prise simple
    val board = new DameBoard()
    val secondBoard = board.play(Coord(3,3,2,4)).play(Coord(0,6,1,5))

    val thirdBoard = secondBoard.play(Coord(2,4,0,6))
    assertTrue(thirdBoard.read(1,5).equals(DameBoard.EMPTY))
  }

  /**
   * Test qu'apres un mouvement le parent est bien enregistrer, afin de gerer les retour arrière
   */
  @Test
  def playTestParent(){
    val board = new DameBoard()

    val moved = board.play(Coord(3,3,2,4))

    assertFalse(moved.equals(board))
    assertTrue(moved.previous() match{
      case Some(b) => b.equals(board)
      case _ => false
    })
  }

  /**
   * Test la transformation en String d'un plateau
   */
  @Test
  def toStringTest(){
    val board = new DameBoard()

    val boardStr = board.toString()
    val expectedStr =
                  "_,x,_,x,_,x,_,x,_,x\nx,_,x,_,x,_,x,_,x,_\n"+
                  "_,x,_,x,_,x,_,x,_,x\nx,_,x,_,x,_,x,_,x,_\n"+
                  "_,_,_,_,_,_,_,_,_,_\n"+"_,_,_,_,_,_,_,_,_,_\n"+
                  "_,o,_,o,_,o,_,o,_,o\no,_,o,_,o,_,o,_,o,_\n"+
                  "_,o,_,o,_,o,_,o,_,o\no,_,o,_,o,_,o,_,o,_"
    assertTrue(boardStr.equals(expectedStr))
  }

  @Test
  def fromStringTest(){
    val strBoard =
      "_,x,_,x,_,x,_,x,_,x\nx,_,x,_,x,_,x,_,x,_\n"+
      "_,x,_,x,_,x,_,x,_,x\nx,_,x,_,x,_,x,_,x,_\n"+
      "_,_,_,_,_,_,_,_,_,_\n"+"_,_,_,_,_,_,_,_,_,_\n"+
      "_,o,_,o,_,o,_,o,_,o\no,_,o,_,o,_,o,_,o,_\n"+
      "_,o,_,o,_,o,_,o,_,o\no,_,o,_,o,_,o,_,o,_"
    val boardFromString = new DameBoard(strBoard)
    val board = new DameBoard()

    for(y <- 0 to 9){
      for(x <- 0 to 0){
        board.read(x,y).equals(boardFromString.read(x,y))
      }
    }
  }

  /**
   * Test la lecture de case du plateau, des blancs, des noirs et des vides
   */
  @Test
  def readTest(){
    val board = new DameBoard()

    //case en haut a gauche
    assertTrue(board.read(0,0).equals(DameBoard.WHITE))
    //en bas a droite
    assertTrue(board.read(9,9).equals(DameBoard.BLACK))
    //deuxieme case en haut a gauche
    assertTrue(board.read(1,0).equals(DameBoard.EMPTY))
    //premiere case de la deuxieme ligne
    assertTrue(board.read(0,1).equals(DameBoard.EMPTY))
    //premiere case avec un blanc
    assertTrue(board.read(1,6).equals(DameBoard.EMPTY))

    assertTrue(board.read(0,7).equals(DameBoard.EMPTY))
  }

}
