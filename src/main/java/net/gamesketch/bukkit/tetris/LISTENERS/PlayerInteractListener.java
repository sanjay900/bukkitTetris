package net.gamesketch.bukkit.tetris.LISTENERS;

import net.gamesketch.bukkit.tetris.Core;
import net.gamesketch.bukkit.tetris.GAME.Game;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.sanjay900.nmsUtil.events.PlayerPushedKeyEvent;

public class PlayerInteractListener
implements Listener
{

	public static void doInventoryUpdate(final Player player, Plugin plugin) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				player.updateInventory();
			}
		}, 1L);
	}

	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
		doInventoryUpdate(event.getPlayer(), Core.cplugin);


	}
	@EventHandler
	public void keyboard(PlayerPushedKeyEvent evt) {
		Settings.performButtonAction(Core.getLocalPlayer(evt.getPlayer()).getGame(), evt.getButtonsPressed()); 
	}
	@EventHandler
	public void menuMove(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			if (event.getInventory().getType() != InventoryType.CRAFTING
					&& event.getInventory().getType() != InventoryType.PLAYER) {

				event.setCancelled(true);
				doInventoryUpdate(p, Core.cplugin);


			}
		}
	}

}
