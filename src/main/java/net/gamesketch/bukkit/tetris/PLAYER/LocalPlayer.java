package net.gamesketch.bukkit.tetris.PLAYER;

import net.gamesketch.bukkit.tetris.GAME.Game;
import org.bukkit.entity.Player;

public class LocalPlayer
{
  Player player;
  Game game;
  boolean isTbuilding;
  
  public LocalPlayer(Player player)
  {
    this.player = player;
    this.game = null;
    this.isTbuilding = false;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public Game getGame()
  {
    return this.game;
  }
  
  public Game setGame(Game game)
  {
    if (this.game != null) {
      this.game.restore();
    }
    this.game = game;
    return game;
  }
  
  public boolean isTbuilding()
  {
    return this.isTbuilding;
  }
  
  public void setTbuilding(boolean b)
  {
    this.isTbuilding = b;
  }
}
