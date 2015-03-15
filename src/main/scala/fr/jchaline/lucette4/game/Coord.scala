package fr.jchaline.lucette4.game

/**
 * Classe permettant d'enregistrer des coordonnées
 */
class Coord(val positions : Int*) {

}

/**
 * Compagnion object, avec constructeur simplifié
 */
object Coord{
  def apply(pos : Int*)={
    new Coord(pos:_*)
  }
}
