package fr.jchaline.lucette4.game

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert._
import org.junit._

@RunWith(classOf[MockitoJUnitRunner])
class CoordTest {

  @Test
  def concatTest(){
    val first = Coord(1,2)
    val second = Coord(3,4)

    assertTrue(Coord(1,2,3,4).equals(first++second))
    assertFalse(Coord(1,2,3,4).equals(second++first))
    assertTrue(Coord(3,4,1,2).equals(second++first))
    assertFalse(Coord(3,4,1,2).equals(first++second))
    assertTrue(Coord(1,2,1,2).equals(first++first))
    assertTrue(Coord(1,2,1,2,1,2).equals(first++first++first))
    assertTrue(Coord(1,2,3,4,1,2).equals(first++second++first))
  }

  @Test
  def getTest(): Unit ={
    val coord  = Coord(1,2,3,4)

    assertTrue(coord.get(0)==1)
    assertTrue(coord.get(1)==2)
    assertTrue(coord.get(2)==3)
    assertTrue(coord.get(3)==4)
  }

  @Test
  def equalsTest(): Unit ={
    val first = Coord(1,2)
    val second = Coord(3,4)
    val third = Coord(1,2)

    assertTrue(first.equals(third))
    assertTrue(third.equals(first))
    assertFalse(second.equals(first))
    assertTrue(first.equals(Coord(1,2)))
  }

}
