package fr.jchaline.lucette4.game

/**
 * Classe permettant d'enregistrer des coordonnées
 */
class Coord(val _positions : Int*) {

  override def toString()={
    _positions.map(_.toString).reduceLeft{_+","+_}
  }

  /**
   * Lecture d'une propriété des coordonnées
   * @param pos indice de la position
   * @return la valeur de la propriété
   */
  def get(pos:Int):Int={
    _positions(pos)
  }

  /**
   * Définition de l'opération de concaténation entre plusieurs Coord.
   * Coord(1,2)++Coord(3,4)==Coord(1,2,3,4)
   * @param other deuxième partie de la concaténation
   * @return le résultat de la concaténation
   */
  def ++(other:Coord)={
    new Coord(_positions.toList++other._positions.toList:_*)
  }

  /**
   * Surcharge du test d'égalité, détermine si un autre objet possède exactement les mêmes coordonnées
   * @param other autre objet comparé
   * @return True si mêmes coordonnées, False sinon
   */
  override def equals(other:Any)= other match {
    case that:Coord => {
      if(_positions.size==that._positions.size) {
        var samePos = true
        for (i <- 0 to that._positions.size-1) {
          samePos &= that._positions(i) == this._positions(i)
        }
        samePos
      }
      else{
        false
      }
    }
    case _ => false
  }

  /**
   * Sépare l'objet en différents objet en groupant les indices par 4 (par défaut) ou par le nombre fourni
   * @param nb nombre d'élément par groupe du split
   * @return liste des groupes de coordonnées
   */
  def split(nb:Int=4):List[Coord]={
    _positions.grouped(nb).toList.map(Coord(_:_*))
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
