package net.gamesketch.bukkit.tetris.LISTENERS;

import net.gamesketch.bukkit.tetris.Core;
import net.gamesketch.bukkit.tetris.PLAYER.LocalPlayer;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;
import net.gamesketch.bukkit.tetris.TetrisLocation;
import net.gamesketch.bukkit.tetris.TetrisLocations;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockDestroyListener
  implements Listener
{
  @EventHandler(priority=EventPriority.HIGH)
  public void onBlockBreak(BlockBreakEvent event)
  {
    boolean removeFromTetrisLocList = false;
    TetrisLocation blo = null;
    for (TetrisLocation l : TetrisLocations.getList()) {
      if (l.compare(event.getBlock()))
      {
        LocalPlayer lp = Core.getLocalPlayer(event.getPlayer());
        if (lp.isTbuilding())
        {
          removeFromTetrisLocList = true;
          blo = l;
        }
        else
        {
          lp.getPlayer().sendMessage("Only players in TBuild mode can destroy tetris locations");
          event.setCancelled(true);
          return;
        }
      }
    }
    if (removeFromTetrisLocList)
    {
      TetrisLocations.getList().remove(blo);
      event.getPlayer().sendMessage("Removed tetris block");
    }
    if (Settings.canDestroyField) {
      return;
    }
    for (LocalPlayer lp : Core.PLAYERDATA) {
      if ((lp.getGame() != null) && 
        (lp.getGame().isInsideBoundaries(event.getBlock())))
      {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
          event.getPlayer().sendMessage("You can't destroy the tetris field controlled by " + lp.getPlayer().getName());
        }
        event.setCancelled(true);
        return;
      }
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onBlockPlace(BlockPlaceEvent event)
  {
    LocalPlayer thislp = Core.getLocalPlayer(event.getPlayer());
    if (thislp.isTbuilding())
    {
    	int maxId = 0;
    	for (TetrisLocation tl : TetrisLocations.getList()) {
    		if (tl.getId() > maxId) maxId = tl.getId();
    	}
      TetrisLocations.getList().add(new TetrisLocation(maxId+1, event.getBlock()));
      event.getPlayer().sendMessage("Tetris block added, Field id: "+String.valueOf(maxId));
    }
    if (Settings.canDestroyField) {
      return;
    }
    for (LocalPlayer lp : Core.PLAYERDATA) {
      if ((lp.getGame() != null) && 
        (lp.getGame().isInsideBoundaries(event.getBlockPlaced())))
      {
        event.getPlayer().sendMessage("You can't build in a tetris field controlled by " + lp.getPlayer().getName());
        event.setCancelled(true);
        return;
      }
    }
  }
}
