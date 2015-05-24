package net.gamesketch.bukkit.tetris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.gamesketch.bukkit.tetris.FIELD.TagIdGenerator;
import net.gamesketch.bukkit.tetris.GAME.Game;
import net.gamesketch.bukkit.tetris.HIGHSCORE.HighScore;
import net.gamesketch.bukkit.tetris.LISTENERS.Listeners;
import net.gamesketch.bukkit.tetris.PLAYER.LocalPlayer;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.nkzawa.socketio.client.Socket;
import com.sanjay900.eyrePlugin.EyrePlugin;
import com.sanjay900.nmsUtil.NMSUtil;

public class Core
extends JavaPlugin
{
	public static List<LocalPlayer> PLAYERDATA;
	public static Map<Player, Location> SPECDATA;
	public static Logger log;
	public static Core cplugin;
	public TagIdGenerator tagIdGenerator;
	public Socket client;
	public NMSUtil nmsutil;
	public void onEnable()
	{
		nmsutil = (NMSUtil) Bukkit.getPluginManager().getPlugin("nmsUtils");
		client = ((EyrePlugin)Bukkit.getPluginManager().getPlugin("EyrePlugin")).getHandler().client;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		tagIdGenerator = new TagIdGenerator();
		log = getLogger();
		PLAYERDATA = new ArrayList<LocalPlayer>();
		SPECDATA = new HashMap<Player, Location>();
		cplugin = this;

		Settings.Load();
		Settings.Save();
			TetrisLocations.Load();
		
		Listeners.init(this);

		HighScore.initScores();

		PluginDescriptionFile pdfFile = getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		
	}

	public void onDisable()
	{
		for (LocalPlayer p : PLAYERDATA) {
			if (p.getGame() != null) {
				p.getGame().restore();
			}
		}
		if (Settings.useAdminLocations) {
			TetrisLocations.Save();
		}

	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		String comm = command.getName().toLowerCase();
		final Player p = (Player)sender;
		if ((comm.equals("tetris")) && (args.length == 0))
		{
		
			LocalPlayer player = getLocalPlayer(p);
			if (player.getGame() != null)
			{
				player.setGame(null);
				
			}
			else
			{
				if (TetrisLocations.getList().isEmpty())
				{
					p.sendMessage("You can only start tetris on Admin-defined areas");
					return true;
				}
				TetrisLocation loc = getNextGame();
				
				if (loc == null)
				{
					p.sendMessage("Currently all the games are in use, try again later!");
					return true;


				}
				player.setGame(new Game(p, this, loc));
			}
		}
		if ((comm.equals("highscore")) || (comm.equals("highscores")) || ((comm.equals("tetris")) && (args.length > 0) && (args[0].contains("score"))))
		{
			p.sendMessage(ChatColor.GREEN + "=======" + ChatColor.LIGHT_PURPLE + "Current Highscores:" + ChatColor.GREEN + "======");
			p.sendMessage(ChatColor.GREEN + "=" + ChatColor.YELLOW + "First:  " + HighScore.getHighScore(1).getPlayer() + " - " + HighScore.getHighScore(1).getScore());
			p.sendMessage(ChatColor.GREEN + "=" + ChatColor.WHITE + "Second: " + HighScore.getHighScore(2).getPlayer() + " - " + HighScore.getHighScore(2).getScore());
			p.sendMessage(ChatColor.GREEN + "=" + ChatColor.RED + "Third:  " + HighScore.getHighScore(3).getPlayer() + " - " + HighScore.getHighScore(3).getScore());
			p.sendMessage(ChatColor.GREEN + "=" + ChatColor.GRAY + "Fourth: " + HighScore.getHighScore(4).getPlayer() + " - " + HighScore.getHighScore(4).getScore());
			p.sendMessage(ChatColor.GREEN + "=" + ChatColor.GRAY + "Fifth:  " + HighScore.getHighScore(5).getPlayer() + " - " + HighScore.getHighScore(5).getScore());
			p.sendMessage(ChatColor.GREEN + "==============================");
		}
		if (((comm.equals("tspec")) || (comm.equals("twatch")) || ((comm.equals("tetris")) && (args.length > 1) && ((args[0].contains("spec")) || (args[0].contains("watch"))))) && (Settings.canSpectate))
		{
			if (args.length < 1) {
				return false;
			}
			Player target = getServer().getPlayer(comm.equals("tetris") ? args[1] : args[0]);
			if (target == null) {
				return false;
			}
			if (getLocalPlayer(target).getGame() == null) {
				return false;
			}
			getLocalPlayer(target).getGame().addSpectator(p);
			p.sendMessage("You are now spectating " + target.getDisplayName() + "'s Tetris game.  You will automatically be teleported out when it is over.");
		}
		if (((comm.equals("tbuild")) || ((comm.equals("tetris")) && (args.length > 0) && (args[0].contains("build")))))
		{
			if (!hasPermission(p, "tetris.create")) {}
			LocalPlayer lp = getLocalPlayer(p);
			lp.setTbuilding(!lp.isTbuilding());
			p.sendMessage("Tetris-build mode set to " + lp.isTbuilding());
			if (lp.isTbuilding()) {
				p.sendMessage("Place any block to create a Tetris location block.  Use /tbuild again to exit Tetris-build mode.");
			}
			TetrisLocations.Save();
			p.sendMessage("Saved all current Tetris location blocks.");
		}
		return true;
	}
	public static Game getGameById(int id) {
		for (LocalPlayer cur : PLAYERDATA) {
			if (cur.getGame() != null) {
				if (cur.getGame().getLoc().getId() == id) {
					return cur.getGame();
				}
			}
		}
		return null;

	}
	public static TetrisLocation getNextGame() {
		for (TetrisLocation l : TetrisLocations.getList()) {
			if (getGameById(l.getId())==null) {
				return l;
			}
		}
		return null;
		
	}
	public static LocalPlayer getLocalPlayer(Player p)
	{
		for (LocalPlayer cur : PLAYERDATA) {
			if (cur.getPlayer().getName().equals(p.getName())) {
				return cur;
			}
		}
		LocalPlayer LP = new LocalPlayer(p);
		PLAYERDATA.add(LP);
		return LP;
	}

	public static boolean hasPermission(Player p, String node)
	{
		log.info("Permission: " + node);
		return p.hasPermission(node);
	}
}
