package net.gamesketch.bukkit.tetris.LISTENERS;

import net.gamesketch.bukkit.tetris.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener
  implements Listener
{
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    if (Core.getLocalPlayer(event.getPlayer()).getGame() != null) {
      Core.getLocalPlayer(event.getPlayer()).setGame(null);
    }
    Core.getLocalPlayer(event.getPlayer()).setTbuilding(false);
  }
  /*TODO: SOCKETIO
  @EventHandler
	public void onMessage(final ServerRecieveMessageEvent messageEvent) throws SQLException {
		if (messageEvent.getMessage().equals("tetris.play")) {
			
			
				
				
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Core.cplugin, new Runnable(){

				@Override
				public void run() {
					UUID pl;
					try {
						pl = UUID.fromString(messageEvent.getDataStream().readUTF());
					} catch (IOException e) {
						return;
					}
					final Player p = Bukkit.getPlayer(pl);
					LocalPlayer player = Core.getLocalPlayer(p);
					if (player.getGame() != null)
					{
						player.setGame(null);
					}
					else
					{
						if (TetrisLocations.getList().isEmpty())
						{
							TetrisLocations.Load();
							if (TetrisLocations.getList().isEmpty())
							{
								TetrisLocation loc = Core.getNextGame();
								
								if (loc == null)
								{
									p.sendMessage("Currently all the games are in use, try again later!");
									ByteArrayDataOutput out = ByteStreams.newDataOutput();
				   					 out.writeUTF("Connect");
				   					 out.writeUTF("lobby");
				   					 p.sendPluginMessage(Core.cplugin, "BungeeCord", out.toByteArray());
									return;


								}
								player.setGame(new Game(p, Core.cplugin, loc));
								return;
							}
							
						}
						TetrisLocation loc = Core.getNextGame();
						
						if (loc == null)
						{
							p.sendMessage("Currently all the games are in use, try again later!");
							ByteArrayDataOutput out = ByteStreams.newDataOutput();
		   					 out.writeUTF("Connect");
		   					 out.writeUTF("lobby");
		   					 p.sendPluginMessage(Core.cplugin, "BungeeCord", out.toByteArray());
							return;


						}
						player.setGame(new Game(p, Core.cplugin, loc));
					}
				}}, 20L);

		}
	}
	*/
}
