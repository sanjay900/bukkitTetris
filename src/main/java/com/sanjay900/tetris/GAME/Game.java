package com.sanjay900.tetris.GAME;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.sanjay900.nmsUtil.movementController.AbstractFrozenController;
import com.sanjay900.nmsUtil.movementController.ControllerFactory;
import com.sanjay900.nmsUtil.util.V10BlockLocation;
import com.sanjay900.tetris.Core;
import com.sanjay900.tetris.FIELD.Field;

public class Game
{
	Player player;
	Field field;
	Gamedata data;
	GameLoop loop;
	int loopid;
	Location prefloc;
	Location origloc;
	AbstractFrozenController block;
	V10BlockLocation loc;
	private Core plugin;
	public Game(Player p, Core plugin)
	{
		this.plugin = plugin;
		this.player = p;
		loc = new V10BlockLocation(p.getWorld().getBlockAt(12, 67, -1));
		this.field = new Field(loc.getHandle().getBlock().getRelative(BlockFace.UP).getLocation());
		if (!this.field.construct())
		{
			this.field.restore();
			return;
		}
		this.data = new Gamedata(this.field, this);
		this.loop = new GameLoop(this);
		this.origloc = loc.getHandle().getBlock().getRelative(BlockFace.UP).getLocation();
		this.origloc.setDirection(new Vector(-1,0,0));
		p.teleport(origloc);
		this.prefloc = origloc.getBlock().getLocation().clone();
		this.prefloc.setX(this.prefloc.getX() + 1.0D);
		this.prefloc.setZ(this.prefloc.getZ() + 2.0D);
		this.prefloc.setPitch(0.0F);
		this.prefloc.setYaw(0.0F);
		block = new ControllerFactory().withLocation(loc.getHandle().getBlock().getRelative(BlockFace.UP,3).getLocation()).withPlayer(p).build();
		p.getInventory().clear();
		ItemStack exit = new ItemStack(Material.BARRIER,1);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatColor.RED+"Exit Game");
		exit.setItemMeta(exitMeta);
		p.getInventory().setItem(8,exit);
		p.getInventory().setHeldItemSlot(0);
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,255,true));
		Location l = p.getLocation();
		l.setDirection(new Vector(-1,0,0));
		p.teleport(l);
		p.sendMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] "+ChatColor.GREEN+"Welcome to Tetris! To move a block, move left and right. To instantly drop a block, press sneak and jump to rotate.");
		this.loopid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this.loop, 1L, 1L);


	}

	public void restore()
	{
		this.player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		block.remove();

		Bukkit.getServer().shutdown();
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
		p.teleport(getSpectatorLocation());
	}
}
