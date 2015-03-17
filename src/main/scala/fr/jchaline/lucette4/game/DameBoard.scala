package fr.jchaline.lucette4.game

/**
 * Plateau pour le jeu de Dame, immutable
 * Noirs (x) en haut, blancs (o) en bas
 *
 * La structure de donnée des cases n'est pas immutable en elle-même mais la collection n'est pas
 * accessible publiquemement et les opérations de lecture/ecriture ne modifie pas la modifie pas
 *
 * Représentation avec coordonnés :
 *
 * 0 _ x _ x _ x _ x _ x
 * 1 x _ x _ x _ x _ x _
 * 2 _ x _ x _ x _ x _ x
 * 3 x _ x _ x _ x _ x _
 * 4 _ _ _ _ _ _ _ _ _ _
 * 5 _ _ _ _ _ _ _ _ _ _
 * 6 _ o _ o _ o _ o _ o
 * 7 o _ o _ o _ o _ o _
 * 8 _ o _ o _ o _ o _ o
 * 9 o _ o _ o _ o _ o _
 * _ 0 1 2 3 4 5 6 7 8 9
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
   * @param posList liste des déplacements à effectuer
   * @param cases plateau avant le déplacement
   * @return le nouveau plateau suite au mouvement
   */
  private def moveWithCoord(posList:List[Coord], cases : Array[Array[Char]]):DameBoard={
    if(posList.size>0){
      moveWithCoord(posList.slice(1,posList.size),moveWithPos(cases, posList(0)._positions(0), posList(0)._positions(1), posList(0)._positions(2), posList(0)._positions(3)))
    }
    else{
      new DameBoard(cases, _previous:+this)
    }
  }

  /**
   * Effectue le déplacement d'un pion sur le plateau
   * @param cases plateau avant déplacement, non modifié
   * @param x1 abscisse du point de départ
   * @param y1 ordonnée du point de départ
   * @param x2 abscisse du point d'arrivé
   * @param y2 ordonnée du point d'arrivé
   * @return nouvelles instances de cases avec le déplacement effectué et la prise si besoin
   */
  private def moveWithPos(cases : Array[Array[Char]], x1:Int,y1:Int,x2:Int,y2:Int)={
    val clone = cases.map(_.clone())
    val value = clone(y1)(x1)
    clone(y1)(x1) = cases(y2)(x2)
    clone(y2)(x2) = value

    //si distance > 1, pris des cases intermédiaires
    val distance = Math.abs(x1 - x2)
    if(distance>1){
      clone((y2+y1)/2)((x1+x2)/2) = DameBoard.EMPTY
    }
    clone
  }

  /**
   * Déplace un pion sur le plateau en fonction des coordonnées contenus dans le mouvement
   * Réalise un appel chainé en cas de prise multiple ie plus de 2 points
   * Attention, pas de controle de cohérence
   * @param pos positions du mouvement
   * @return le nouveau plateau suite au mouvement
   */
  def play(pos:Coord)={
    moveWithCoord(pos.split(), _cases)
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
  def read(x:Int,y:Int):Char={
    _cases(y)(x)
  }

  /**
   * Lecture d'une case du plateau
   * @param coord coordonnées de la case
   * @return valeur de la case lue
   */
  def read(coord:Coord):Char={
    read(coord._positions(0), coord._positions(1))
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
   * Fonction d'affichage du plateau avec indication sur les lignes/colonnes
   * @return le plateau au format String, plus joli que le toString()
   */
  def niceString()={
    var nice = ""
    for(y <- 0 to 9){
      nice = nice + y
      for(x <- 0 to 9){
        nice = nice + " " + read(x,y)
      }
      nice = nice + '\n'
    }
    nice = nice + "_ 0 1 2 3 4 5 6 7 8 9"
    nice
  }

  /**
   * Détermine si une coordonnées est présente sur le plateau
   * @param coord coordonnées du point
   * @return true si présent, false sinon
   */
  def inBoundary(coord:Coord) = (coord._positions(0) < DIM_X && coord._positions(0) >= 0 && coord._positions(1) < DIM_Y && coord._positions(1) >= 0)

}

/**
 * Compagnion object with constant values
 */
object DameBoard{
  def EMPTY = '_'
  def BLACK = 'x'
  def WHITE = 'o'
}