package net.gamesketch.bukkit.tetris.HIGHSCORE;

import org.bukkit.entity.Player;

public class PlayerHighScore
{
  String player;
  int score;
  
  public PlayerHighScore(Player p, int score)
  {
    this.player = p.getName();
    this.score = score;
  }
  
  public PlayerHighScore(String player, int score)
  {
    this.player = player;
    this.score = score;
  }
  
  public String getPlayer()
  {
    return this.player;
  }
  
  public int getScore()
  {
    return this.score;
  }
  
  public String toString()
  {
    return this.player + ":" + this.score;
  }
}
