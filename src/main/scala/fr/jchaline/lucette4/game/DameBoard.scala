package fr.jchaline.lucette4.game

/**
 * Plateau pour le jeu de Dame, immutable
 * Noirs (x) en haut, blancs (o) en bas
 *
 * La structure de donnée des cases n'est pas immutable en elle-même mais la collection n'est pas
 * accessible publiquemement et les opérations de lecture/ecriture ne modifie pas la modifie pas
 */
class DameBoard(cases : Array[Array[Char]], parents:List[DameBoard]) {

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
   * @param x1 abscisse du point de départ
   * @param y1 ordonnée du point de départ
   * @param x2 abscisse du point d'arrivé
   * @param y2 ordonnée du point d'arrivé
   * @return le nouveau plateau suite au mouvement
   */
  private def moveWithCoord(x1:Int,y1:Int,x2:Int,y2:Int)={
    val clone = cases.map(_.clone())
    val value = clone(y1)(x1)
    clone(y1)(x1) = cases(y2)(x2)
    clone(y2)(x2) = value

    new DameBoard(clone,parents:+this)
  }

  /**
   * Déplace un pion sur le plateau en fonction des coordonnées contenus dans le mouvement
   * @param pos positions du mouvement
   * @return le nouveau plateau suite au mouvement
   */
  def move(pos:Coord)={
    moveWithCoord(pos.positions(0), pos.positions(1), pos.positions(2), pos.positions(3))
  }

  /**
    * Renvoi le plateau précédent s'il existe
   * @return le plateau précédent
   */
  def previous()={
    parents.last
  }

  /**
    * Vrai si un précédent existe
   * @return True si un précédent existe, False sinon
   */
  def hasPrevious()={
    parents.size>0
  }

  /**
   * Lecture d'une case du plateau
   * @param x abscisse de la case à lire
   * @param y ordonnée de la case à lire
   * @return la valeur de la case lue
   */
  def read(x:Int,y:Int)={
    cases(y)(x)
  }

  /**
   * Transformation du plateau en chaine de caractère, avec saut de ligne et virgule en séparateur
   * @return version String du plateau
   */
  override def toString()={
    cases.foldLeft(""){
      (x,y) => x+(if(x.toString().length>0) "\n" else "")+
        y.foldLeft(""){
          (a,b) => a+(if(a.toString().length>0) "," else "")+b
        }
    }
  }
}

/**
 * Compagnion object with constant values
 */
object DameBoard{
  def EMPTY = '_'
  def BLACK = 'x'
  def WHITE = 'o'
}