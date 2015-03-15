package fr.jchaline.lucette4.game

/**
 * Plateau pour le jeu de Dame, immutable
 * Noirs (x) en haut, blancs (o) en bas
 *
 * La structure de donnée des cases n'est pas immutable en elle-même mais la collection n'est pas
 * accessible publiquemement et les opérations de lecture/ecriture ne modifie pas la modifie pas
 */
class DameBoard(val _cases : Array[Array[Char]], val _previous:List[DameBoard]) {

  val DIM_X = 10
  val DIM_Y = 10

  /**
   * Constructeur par defaut du DameBoard, avec les cases prêtes pour une nouvelle partie
   * et une liste de parent vide
   */
  def this()={
    this(
      Array(
        Array('_','x','_','x','_','x','_','x','_','x'),
        Array('x','_','x','_','x','_','x','_','x','_'),
        Array('_','x','_','x','_','x','_','x','_','x'),
        Array('x','_','x','_','x','_','x','_','x','_'),
        Array('_','_','_','_','_','_','_','_','_','_'),
        Array('_','_','_','_','_','_','_','_','_','_'),
        Array('_','o','_','o','_','o','_','o','_','o'),
        Array('o','_','o','_','o','_','o','_','o','_'),
        Array('_','o','_','o','_','o','_','o','_','o'),
        Array('o','_','o','_','o','_','o','_','o','_')
      ),
      List[DameBoard]()
    )
  }

  /**
   * Déplace un pion sur le plateau, de x,y en a,b
   * Supprime les pions intermédiaires
   * Attention, pas de controle de cohérence
   * @param x1 abscisse du point de départ
   * @param y1 ordonnée du point de départ
   * @param x2 abscisse du point d'arrivé
   * @param y2 ordonnée du point d'arrivé
   * @return le nouveau plateau suite au mouvement
   */
  private def moveWithCoord(x1:Int,y1:Int,x2:Int,y2:Int)={
    val clone = _cases.map(_.clone())
    val value = clone(y1)(x1)
    clone(y1)(x1) = _cases(y2)(x2)
    clone(y2)(x2) = value

    //si distance > 1
    val distance = Math.abs(x1 - x2)
    if(distance>1){

    }

    new DameBoard(clone,_previous:+this)
  }

  /**
   * Déplace un pion sur le plateau en fonction des coordonnées contenus dans le mouvement
   * Réalise un appel chainé en cas de prise multiple ie plus de 2 points
   * Attention, pas de controle de cohérence
   * @param pos positions du mouvement
   * @return le nouveau plateau suite au mouvement
   */
  def play(pos:Coord)={
    moveWithCoord(pos._positions(0), pos._positions(1), pos._positions(2), pos._positions(3))
  }

  /**
    * Renvoi le plateau précédent s'il existe
   * @return le plateau précédent
   */
  def previous()={
    _previous.last
  }

  /**
    * Vrai si un précédent existe
   * @return True si un précédent existe, False sinon
   */
  def hasPrevious()={
    _previous.size>0
  }

  /**
   * Lecture d'une case du plateau
   * @param x abscisse de la case à lire
   * @param y ordonnée de la case à lire
   * @return la valeur de la case lue
   */
  def read(x:Int,y:Int)={
    _cases(y)(x)
  }

  /**
   * Transformation du plateau en chaine de caractère, avec saut de ligne et virgule en séparateur
   * @return version String du plateau
   */
  override def toString()={
    _cases.foldLeft(""){
      (x,y) => x+(if(x.toString().length>0) "\n" else "")+
        y.foldLeft(""){
          (a,b) => a+(if(a.toString().length>0) "," else "")+b
        }
    }
  }

  /**
   * Détermine si une coordonnées est présente sur le plateau
   * @param x abscisse de la coordonnée
   * @param y ordonnée de la coordonnée
   * @return true si présent, false sinon
   */
  def inBoundary(x: Int, y: Int) = (x < DIM_X && x >= 0 && y < DIM_Y && y >= 0)

}

/**
 * Compagnion object with constant values
 */
object DameBoard{
  def EMPTY = '_'
  def BLACK = 'x'
  def WHITE = 'o'
}