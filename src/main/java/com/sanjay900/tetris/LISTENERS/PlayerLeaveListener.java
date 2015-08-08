package com.sanjay900.tetris.LISTENERS;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sanjay900.tetris.Core;
import com.sanjay900.tetris.GAME.Game;
import com.sanjay900.tetris.PLAYER.LocalPlayer;

public class PlayerLeaveListener implements Listener
{
	private static Core plugin = Core.getInstance();
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (plugin.getLocalPlayer(event.getPlayer()).getGame() != null) {
			plugin.getLocalPlayer(event.getPlayer()).setGame(null);
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent evt) {
		LocalPlayer player = plugin.getLocalPlayer(evt.getPlayer());
		if (plugin.getGame() != null)
		{
			evt.getPlayer().sendMessage("You are now spectating: "+plugin.getGame().getPlayer().getDisplayName());
			return;
		}
		plugin.setGame(new Game(evt.getPlayer(), Core.getInstance()));
		player.setGame(plugin.getGame());
	}

}
