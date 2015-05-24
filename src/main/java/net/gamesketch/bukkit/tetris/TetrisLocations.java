package net.gamesketch.bukkit.tetris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class TetrisLocations
{
  static List<TetrisLocation> list = new ArrayList<TetrisLocation>();
  static File file = new File("plugins/Tetris/locations");
  static File folder = new File("plugins/Tetris/");
  
  public static void Load()
  {
    if (!checkFile()) {
      return;
    }
    list.clear();
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));
      String s;
      while ((s = in.readLine()) != null)
      {
        if (!s.matches("[0-9]+:(-)*[0-9]+:(-)*[0-9]+:(-)*[0-9]+:[a-zA-Z0-9_ -]+"))
        {
          System.out.println("The following line doesn't match the data regex:");
          System.out.println(s);
        }
        else
        {
          String[] args = s.split(":");
          int x = 0;int y = 0;int z = 0;
          int id = 0;
          try
          {
        	id = Integer.parseInt(args[0]);
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
            z = Integer.parseInt(args[3]);
          }
          catch (NumberFormatException e)
          {
            continue;
          }
          World w = Bukkit.getServer().getWorld(args[4]);
          if (w != null) {
            list.add(new TetrisLocation(id, w, x, y, z));
          }
        }
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();return;
    }
  }
  
  public static void Save()
  {
    if (!checkFile()) {
      return;
    }
    try
    {
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      for (TetrisLocation t : list)
      {
        out.write(t.toString());
        out.newLine();
      }
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();return;
    }
  }
  
  public static boolean checkFile()
  {
    if (!file.exists()) {
      try
      {
        folder.mkdirs();
        file.createNewFile();
      }
      catch (IOException e)
      {
        return false;
      }
    }
    return true;
  }
  
  public static List<TetrisLocation> getList()
  {
    return list;
  }
}
