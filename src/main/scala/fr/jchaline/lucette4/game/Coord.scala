package fr.jchaline.lucette4.game

/**
 * Classe permettant d'enregistrer des coordonnées
 */
class Coord(val _positions : Int*) {

  override def toString()={
    _positions.map(_.toString).reduceLeft{_+","+_}
  }

  override def equals(other:Any)= other match {
    case that:Coord => {
      var samePos = true
      for(i <- 0 to 3){
        samePos &= that._positions(i)==this._positions(i)
      }
      _positions.size==that._positions.size && samePos
    }
    case _ => false
  }
}

/**
 * Compagnion object, avec constructeur simplifié
 */
object Coord{
  def apply(pos : Int*)={
    new Coord(pos:_*)
  }
}
