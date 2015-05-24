package net.gamesketch.bukkit.tetris.LISTENERS;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener
  implements Listener
{
  long lastRun = System.currentTimeMillis();
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerMove(PlayerMoveEvent event)
  {
   
  }
}
