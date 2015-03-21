package fr.jchaline.lucette4.engine

import fr.jchaline.lucette4.game.DameBoard

/**
 * Une partie, contient principalement un plateau de jeu,
 * controle les tentatives de jeu et peut être intéroger pour savoir à qui de jouer
 */
class Game(val _idGame:String, val _idPlayer1:String, val _idPlayer2:String) {

  /**
   * Plateau de jeu courant de la partie
   */
  var board = new DameBoard()



}
