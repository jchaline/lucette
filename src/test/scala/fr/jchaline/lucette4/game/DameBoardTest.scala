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
  def moveTestValue(){
    val board = new DameBoard()

    //assert value before mouvement
    assertTrue(board.read(0,3).equals(DameBoard.BLACK))
    assertTrue(board.read(1,4).equals(DameBoard.EMPTY))

    val moved = board.play(Coord(0,3,1,4))

    //assert immutabilité
    assertTrue(board.read(0,3).equals(DameBoard.BLACK))
    assertTrue(board.read(1,4).equals(DameBoard.EMPTY))

    //assert mouvement sur nouveau plateau
    assertTrue(moved.read(0,3).equals(DameBoard.EMPTY))
    assertTrue(moved.read(1,4).equals(DameBoard.BLACK))

    //assert que le nouveau plateau n'est pas le même
    assertFalse( moved.equals( board ) )
  }

  /**
   * Test une suite de coups et valide le resultat final
   * Déplacements et prise simple
   */
  @Test
  def movesMultiples(){

    //init du plateau et déplacement de pions pour permettre une prise simple
    val board = new DameBoard()
    val secondBoard = board.play(Coord(0,3,1,4)).play(Coord(3,6,2,5))

    val thirdBoard = secondBoard.play(Coord(1,4,3,6))
    assertTrue(thirdBoard.read(2,5).equals(DameBoard.EMPTY))
  }

  /**
   * Test qu'apres un mouvement le parent est bien enregistrer, afin de gerer les retour arrière
   */
  @Test
  def moveTestParent(){
    val board = new DameBoard()

    val moved = board.play( new Coord(0,3,1,4))

    assertFalse(moved.equals(board))
    assertTrue(moved.previous().equals(board))
  }

  /**
   * Test la transformation en String d'un plateau
   */
  @Test
  def toStringTest() {
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

  /**
   * Test la lecture de case du plateau, des blancs, des noirs et des vides
   */
  @Test
  def readTest(){
    val board = new DameBoard()

    //case en haut a gauche
    assertTrue(board.read(0,0).equals(DameBoard.EMPTY))
    //en bas a droite
    assertTrue(board.read(9,9).equals(DameBoard.EMPTY))
    //deuxieme case en haut a gauche
    assertTrue(board.read(1,0).equals(DameBoard.BLACK))
    //premiere case de la deuxieme ligne
    assertTrue(board.read(0,1).equals(DameBoard.BLACK))
    //premiere case avec un blanc
    assertTrue(board.read(1,6).equals(DameBoard.WHITE))

    assertTrue(board.read(0,7).equals(DameBoard.WHITE))
  }

}
