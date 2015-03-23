package fr.jchaline.lucette4.network

import fr.jchaline.lucette4.game.Coord

class RestClient(val _playerId:String ) {

  /**
   * Play moove to server
   */
  def play( move:Coord ):String={
    ""
  }

  /**
   * Ask server to create new game
   */
  def createGame():String={
    ""
  }

  /**
   * Ask server for the turn
   */
  def askTurn( gameId:String ):String={
    ""
  }
}
