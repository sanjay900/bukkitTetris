package com.sanjay900.tetris.LISTENERS;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.sanjay900.nmsUtil.events.PlayerPushedKeyEvent;
import com.sanjay900.tetris.Core;
import com.sanjay900.tetris.SETTINGS.Settings;

public class PlayerInteractListener implements Listener
{
	private static Core plugin = Core.getInstance();
	public static void doInventoryUpdate(final Player player, Plugin plugin) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				player.updateInventory();
			}
		}, 1L);
	}

	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
		doInventoryUpdate(event.getPlayer(), plugin);


	}
	@EventHandler
	public void keyboard(PlayerPushedKeyEvent evt) {
		Settings.performButtonAction(plugin.getLocalPlayer(evt.getPlayer()).getGame(), evt.getButtonsPressed()); 
	}
	@EventHandler
	public void interact(PlayerInteractEvent evt) {
		if (evt.hasItem() && evt.getItem().hasItemMeta() && evt.getItem().getItemMeta().hasDisplayName() && evt.getItem().getItemMeta().getDisplayName().contains("Exit Game")) {
			if (plugin.getLocalPlayer(evt.getPlayer()) != null) plugin.getLocalPlayer(evt.getPlayer()).setGame(null);
		}
		if (!evt.getPlayer().isOp())
			evt.setCancelled(true);
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		if (!evt.getPlayer().isOp())
			evt.setCancelled(true);
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		if (!evt.getPlayer().isOp())
			evt.setCancelled(true);
	}
	@EventHandler
	public void menuMove(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			if (event.getInventory().getType() != InventoryType.CRAFTING
					&& event.getInventory().getType() != InventoryType.PLAYER) {

				event.setCancelled(true);
				doInventoryUpdate(p, plugin);


			}
		}
	}

}
