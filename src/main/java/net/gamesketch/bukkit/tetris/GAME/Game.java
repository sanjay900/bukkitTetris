package net.gamesketch.bukkit.tetris.GAME;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import net.gamesketch.bukkit.tetris.Core;
import net.gamesketch.bukkit.tetris.TetrisLocation;
import net.gamesketch.bukkit.tetris.FIELD.Field;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sanjay900.nmsUtil.fallingblocks.FrozenSandFactory;
import com.sanjay900.nmsUtil.movementController.ControllerFactory;
import com.sanjay900.nmsUtil.movementController.FrozenController;

public class Game
{
	Player player;
	Field field;
	Gamedata data;
	GameButtons button;
	GameLoop loop;
	int loopid;
	Location prefloc;
	Location origloc;
	ItemStack[] InventoryBackup;
	Map<Player, Location> spectatorLocations;
	FrozenController block;
	private TetrisLocation loc;
	private Core plugin;
	public Game(Player p, Core plugin, TetrisLocation loc)
	{
		this.plugin = plugin;
		this.setLoc(loc);
		this.player = p;
		this.field = new Field(loc.getBlock().getRelative(BlockFace.UP).getLocation());
		if (!this.field.construct())
		{
			this.field.restore();
			return;
		}
		this.data = new Gamedata(this.field, this);
		this.button = new GameButtons(this.field);
		this.loop = new GameLoop(this);
		this.origloc = loc.getBlock().getRelative(BlockFace.UP).getLocation();
		this.origloc.setDirection(new Vector(-1,0,0));
		p.teleport(origloc);
		this.prefloc = origloc.getBlock().getLocation().clone();
		this.prefloc.setX(this.prefloc.getX() + 1.0D);
		this.prefloc.setZ(this.prefloc.getZ() + 2.0D);
		this.prefloc.setPitch(0.0F);
		this.prefloc.setYaw(0.0F);
		block = new ControllerFactory(plugin,plugin.nmsutil).withLocation(loc.getBlock().getRelative(BlockFace.UP).getLocation()).withPlayer(p).build();

		this.InventoryBackup = ((ItemStack[])p.getInventory().getContents().clone());
		p.getInventory().clear();

		ItemStack item = new ItemStack(Material.WATCH,1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GOLD+"Rotate Block");
		item.setItemMeta(itemMeta);
		ItemStack exit = new ItemStack(Material.SLIME_BALL,1);
		ItemMeta exitMeta = item.getItemMeta();
		exitMeta.setDisplayName(ChatColor.RED+"Exit Game");
		exit.setItemMeta(exitMeta);

		p.getInventory().setItem(0,item);
		p.getInventory().setItem(8,exit);
		p.getInventory().setHeldItemSlot(0);
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,255,true));
		Location l = p.getLocation();
		l.setDirection(new Vector(-1,0,0));
		p.teleport(l);
		this.spectatorLocations = new HashMap<Player, Location>();
		p.sendMessage(ChatColor.YELLOW+"Please wait while your game starts.");
		this.loopid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this.loop, 80L, 1L);


	}

	public void restore()
	{
		String text = "0";
		BufferedImage bufferedImage = new BufferedImage(1024, 128,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.setFont(data.customFont);
		graphics.drawString(text, 10, 100);
		for (ScoreMapRenderer r : data.maps.values()) {
			r.setScore(bufferedImage);
		}
		data.maps.clear();
		this.player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		block.remove();
		this.player.getInventory().setContents(this.InventoryBackup);

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("Lobby");
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		this.field.restore();
		this.player.teleport(this.origloc);
		for (Player p : this.spectatorLocations.keySet()) {
			p.teleport((Location)this.spectatorLocations.get(p));
		}
		this.spectatorLocations.clear();
		Bukkit.getServer().getScheduler().cancelTask(this.loopid);
	}

	public boolean isInsideBoundaries(Block b)
	{
		if (b.getX() < this.field.lc.getX()) {
			return false;
		}
		if (b.getX() > this.field.uc.getX()) {
			return false;
		}
		if (b.getY() < this.field.uc.getY()) {
			return false;
		}
		if (b.getY() > this.field.lc.getY()) {
			return false;
		}
		if (b.getZ() < this.field.uc.getZ()) {
			return false;
		}
		if (b.getZ() > this.field.lc.getZ()) {
			return false;
		}
		return b.getWorld() == this.field.uc.getWorld();
	}

	public Gamedata data()
	{
		return this.data;
	}

	public void cancelGameloop()
	{
		Bukkit.getServer().getScheduler().cancelTask(this.loopid);
	}

	public Location getPrefferedLocation()
	{
		return this.prefloc;
	}

	public Location getSpectatorLocation()
	{
		return this.field.lc.getBlock().getRelative(8, 1, 1).getLocation();
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public void addSpectator(Player p)
	{
		this.spectatorLocations.put(p, p.getLocation());
		p.teleport(getSpectatorLocation());
	}

	public TetrisLocation getLoc() {
		return loc;
	}

	public void setLoc(TetrisLocation loc) {
		this.loc = loc;
	}
}
