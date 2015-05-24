package net.gamesketch.bukkit.tetris.HIGHSCORE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.gamesketch.bukkit.tetris.GAME.Game;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class HighScore
{
  static File file = new File("plugins/Tetris/highscore.score");
  static File folder = new File("plugins/Tetris/");
  static PlayerHighScore first = new PlayerHighScore("Nobody", 0);
  static PlayerHighScore second = new PlayerHighScore("Nobody", 0);
  static PlayerHighScore third = new PlayerHighScore("Nobody", 0);
  static PlayerHighScore fourth = new PlayerHighScore("Nobody", 0);
  static PlayerHighScore fifth = new PlayerHighScore("Nobody", 0);
  
  public static void postHighscore(Game game)
  {
    if (!checkfile()) {
      return;
    }
    if (!initScores()) {
      return;
    }
    if (game.data().getScore() >= fifth.getScore())
    {
      int score = game.data().getScore();
      if (Settings.UniqueHighscore) {
        enforceUniqueHighscore(game);
      }
      if (score > first.getScore())
      {
        fifth = fourth;
        fourth = third;
        third = second;
        second = first;
        first = new PlayerHighScore(game.getPlayer(), score);
        if (Settings.AnouncesHighscore) {
          Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] " + ChatColor.GREEN + game.getPlayer().getName() + ChatColor.WHITE + " has beaten the first highscore with a score of " + game.data().getScore() + "!");
        }
      }
      else if (score > second.getScore())
      {
        if ((!Settings.UniqueHighscore) || (!first.getPlayer().equals(game.getPlayer().getName())))
        {
          fifth = fourth;
          fourth = third;
          third = second;
          second = new PlayerHighScore(game.getPlayer(), score);
        }
        if (Settings.AnouncesHighscore) {
          Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] " + ChatColor.GREEN + game.getPlayer().getName() + ChatColor.WHITE + " has beaten the second highscore with a score of " + game.data().getScore() + "!");
        }
      }
      else if (score > third.getScore())
      {
        if ((!Settings.UniqueHighscore) || ((!first.getPlayer().equals(game.getPlayer().getName())) && (!second.getPlayer().equals(game.getPlayer().getName()))))
        {
          fifth = fourth;
          fourth = third;
          third = new PlayerHighScore(game.getPlayer(), score);
        }
        if (Settings.AnouncesHighscore) {
          Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] " + ChatColor.GREEN + game.getPlayer().getName() + ChatColor.WHITE + " has beaten the third highscore with a score of " + game.data().getScore() + "!");
        }
      }
      else if (score > fourth.getScore())
      {
        if ((!Settings.UniqueHighscore) || ((!first.getPlayer().equals(game.getPlayer().getName())) && (!second.getPlayer().equals(game.getPlayer().getName())) && 
          (!third.getPlayer().equals(game.getPlayer().getName()))))
        {
          fifth = fourth;
          fourth = new PlayerHighScore(game.getPlayer(), score);
        }
        if (Settings.AnouncesHighscore) {
          Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] " + ChatColor.GREEN + game.getPlayer().getName() + ChatColor.WHITE + " has beaten the fourth highscore with a score of " + game.data().getScore() + "!");
        }
      }
      else if (score > fifth.getScore())
      {
        if ((!Settings.UniqueHighscore) || ((!first.getPlayer().equals(game.getPlayer().getName())) && (!second.getPlayer().equals(game.getPlayer().getName())) && 
          (!third.getPlayer().equals(game.getPlayer().getName())) && (!fourth.getPlayer().equals(game.getPlayer().getName())))) {
          fifth = new PlayerHighScore(game.getPlayer(), score);
        }
        if (Settings.AnouncesHighscore) {
          Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] " + ChatColor.GREEN + game.getPlayer().getName() + ChatColor.WHITE + " has beaten the fifth highscore with a score of " + game.data().getScore() + "!");
        }
      }
    }
    else
    {
      return;
    }
    try
    {
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      
      out.write("first:" + first.toString());out.newLine();
      out.write("second:" + second.toString());out.newLine();
      out.write("third:" + third.toString());out.newLine();
      out.write("fourth:" + fourth.toString());out.newLine();
      out.write("fifth:" + fifth.toString());out.newLine();
      
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private static boolean checkfile()
  {
    if (!file.exists()) {
      try
      {
        folder.mkdirs();
        file.createNewFile();
        return true;
      }
      catch (IOException e)
      {
        return false;
      }
    }
    return true;
  }
  
  public static boolean initScores()
  {
    if (!checkfile()) {
      return false;
    }
    if (first.getScore() > 0) {
      return true;
    }
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));
      String s;
      while ((s = in.readLine()) != null)
      {
        if (s.matches("[first|second|third|fourth|fifth]+:[a-zA-Z0-9_]+:[0-9]+"))
        {
          String player = s.split(":")[1];
          int score = Integer.parseInt(s.split(":")[2]);
          if (s.matches("first:[a-zA-Z0-9_]+:[0-9]+")) {
            first = new PlayerHighScore(player, score);
          }
          if (s.matches("second:[a-zA-Z0-9_]+:[0-9]+")) {
            second = new PlayerHighScore(player, score);
          }
          if (s.matches("third:[a-zA-Z0-9_]+:[0-9]+")) {
            third = new PlayerHighScore(player, score);
          }
          if (s.matches("fourth:[a-zA-Z0-9_]+:[0-9]+")) {
            fourth = new PlayerHighScore(player, score);
          }
          if (s.matches("fifth:[a-zA-Z0-9_]+:[0-9]+")) {
            fifth = new PlayerHighScore(player, score);
          }
        }
      }
      in.close();
    }
    catch (IOException e)
    {
      return false;
    }
    return true;
  }
  
  public static PlayerHighScore getHighScore(int r)
  {
    if (r == 1) {
      return first;
    }
    if (r == 2) {
      return second;
    }
    if (r == 3) {
      return third;
    }
    if (r == 4) {
      return fourth;
    }
    if (r == 5) {
      return fifth;
    }
    return null;
  }
  
  private static void enforceUniqueHighscore(Game game)
  {
    int score = game.data().getScore();
    if (score > fifth.getScore())
    {
      if (fifth.getPlayer().equals(game.getPlayer().getName())) {
        fifth = new PlayerHighScore("Nobody", 0);
      }
      if (score > fourth.getScore())
      {
        if (fourth.getPlayer().equals(game.getPlayer().getName())) {
          fourth = new PlayerHighScore("Nobody", 0);
        }
        if (score > third.getScore())
        {
          if (third.getPlayer().equals(game.getPlayer().getName())) {
            third = new PlayerHighScore("Nobody", 0);
          }
          if (score > second.getScore())
          {
            if (second.getPlayer().equals(game.getPlayer().getName())) {
              second = new PlayerHighScore("Nobody", 0);
            }
            if (score > first.getScore()) {
              if (first.getPlayer().equals(game.getPlayer().getName())) {
                first = new PlayerHighScore("Nobody", 0);
              }
            }
          }
        }
      }
    }
    if (fourth.getScore() == 0)
    {
      fourth = fifth;
      fifth = new PlayerHighScore("Nobody", 0);
    }
    if (third.getScore() == 0)
    {
      third = fourth;
      fourth = fifth;
      fifth = new PlayerHighScore("Nobody", 0);
    }
    if (second.getScore() == 0)
    {
      second = third;
      third = fourth;
      fourth = fifth;
      fifth = new PlayerHighScore("Nobody", 0);
    }
    if (first.getScore() == 0)
    {
      first = second;
      second = third;
      third = fourth;
      fourth = fifth;
      fifth = new PlayerHighScore("Nobody", 0);
    }
  }
}
