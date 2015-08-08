package com.sanjay900.tetris;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.sanjay900.eyrePlugin.EyrePlugin;
import com.sanjay900.eyrePlugin.sockets.SocketIOHandler;
import com.sanjay900.nmsUtil.NMSUtil;
import com.sanjay900.tetris.GAME.Game;
import com.sanjay900.tetris.HIGHSCORE.HighScore;
import com.sanjay900.tetris.HIGHSCORE.PlayerHighScore;
import com.sanjay900.tetris.LISTENERS.Listeners;
import com.sanjay900.tetris.PLAYER.LocalPlayer;
import com.sanjay900.tetris.SETTINGS.Database;

import lombok.Getter;
import lombok.Setter;
@Getter
public class Core extends JavaPlugin
{
	private List<LocalPlayer> PLAYERDATA;
	private Map<Player, Location> SPECDATA;
	@Getter
	private static Core instance;
	public SocketIOHandler client;
	public NMSUtil nmsutil;
	@Setter
	private Game game;
	private String serverid;
	private HighScore highScore;
	private Database Db;
	public void onEnable()
	{
		
		instance = this;	
		highScore = new HighScore();
		serverid = ((EyrePlugin)Bukkit.getPluginManager().getPlugin("EyrePlugin")).getServerName().replace("tetris", "");
		System.out.println("Server Tetris "+serverid+" initilized!");
		nmsutil = (NMSUtil) Bukkit.getPluginManager().getPlugin("nmsUtils");
		client = ((EyrePlugin)Bukkit.getPluginManager().getPlugin("EyrePlugin")).getHandler();
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		PLAYERDATA = new ArrayList<LocalPlayer>();
		SPECDATA = new HashMap<Player, Location>();	
		Listeners.init(this);
		client.client.on("tetris", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				try {
				JSONObject obj = (JSONObject) args[0];
				int score = obj.getInt("score");
				int lines = obj.getInt("lines");
				UUID uuid = UUID.fromString(obj.getString("uuid"));
					String player = obj.getString("player");
					highScore.setScore(uuid, new PlayerHighScore(player,uuid,score,lines));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	public void onDisable()
	{
		for (LocalPlayer p : PLAYERDATA) {
			if (p.getGame() != null) {
				p.getGame().restore();
			}
		}

	}
	
	public LocalPlayer getLocalPlayer(Player p)
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
}
