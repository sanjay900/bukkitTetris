package net.gamesketch.bukkit.tetris.LISTENERS;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Listeners
{
  private static Listener playerInteractListener = new PlayerInteractListener();
  private static Listener playerMoveListener = new PlayerMoveListener();
  private static Listener playerQuitListener = new PlayerLeaveListener();
  private static Listener blockListener = new BlockDestroyListener();
  
  public static void init(Plugin plugin)
  {
    PluginManager pm = Bukkit.getServer().getPluginManager();
    pm.registerEvents(playerInteractListener, plugin);
    pm.registerEvents(playerMoveListener, plugin);
    pm.registerEvents(playerQuitListener, plugin);
    pm.registerEvents(blockListener, plugin);
  }
}
