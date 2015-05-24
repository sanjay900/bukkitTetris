package net.gamesketch.bukkit.tetris.FIELD;

import net.gamesketch.bukkit.tetris.SETTINGS.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Platform
{
  int x;
  int y;
  int z;
  World world;
  
  public Platform(Location loc)
  {
    Block ref = loc.getBlock();
    this.x = ref.getX();
    this.y = ref.getY();
    this.z = ref.getZ();
    this.world = ref.getWorld();
  }
  
  public boolean construct()
  {
    Block ref = this.world.getBlockAt(this.x, this.y, this.z);
    
    ref.getRelative(-1, -1, 0).setType(Material.GLASS);
    ref.getRelative(0, -1, 0).setType(Material.GLASS);
    ref.getRelative(1, -1, 0).setType(Material.GLASS);
    ref.getRelative(2, -1, 0).setType(Material.GLASS);
    ref.getRelative(-1, -1, 1).setType(Material.GLASS);
    ref.getRelative(0, -1, 1).setType(Material.GLASS);
    ref.getRelative(1, -1, 1).setType(Material.GLASS);
    ref.getRelative(2, -1, 1).setType(Material.GLASS);
    ref.getRelative(-1, -1, 2).setType(Material.GLASS);
    ref.getRelative(0, -1, 2).setType(Material.GLASS);
    ref.getRelative(1, -1, 2).setType(Material.GLASS);
    ref.getRelative(2, -1, 2).setType(Material.GLASS);
    
    ref.getRelative(-1, -1, 3).setType(Material.DIAMOND_BLOCK);
    ref.getRelative(0, -1, 3).setType(Material.DIAMOND_BLOCK);
    ref.getRelative(1, -1, 3).setType(Material.DIAMOND_BLOCK);
    ref.getRelative(2, -1, 3).setType(Material.DIAMOND_BLOCK);
    ref.getRelative(-1, 0, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(-1, 1, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(-1, 2, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(2, 0, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(2, 1, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(2, 2, 3).setType(Material.GOLD_BLOCK);
    ref.getRelative(-1, 3, 3).setType(Material.IRON_BLOCK);
    ref.getRelative(0, 3, 3).setType(Material.IRON_BLOCK);
    ref.getRelative(1, 3, 3).setType(Material.IRON_BLOCK);
    ref.getRelative(2, 3, 3).setType(Material.IRON_BLOCK);
    
    ref.getRelative(2, 0, 2).setTypeIdAndData(68, (byte)2, true);
    if (Settings.isPlayingMusic) {
      ref.getRelative(-2, -1, 0).setType(Material.NOTE_BLOCK);
    }
    ref.getRelative(-1, -7, -1).setTypeId(35);
    ref.getRelative(-1, -8, -1).setTypeId(35);
    ref.getRelative(-1, -9, -1).setTypeId(35);
    ref.getRelative(-1, -10, -1).setTypeId(35);
    ref.getRelative(0, -7, -1).setTypeId(35);
    ref.getRelative(0, -8, -1).setTypeId(0);
    ref.getRelative(0, -9, -1).setTypeId(0);
    ref.getRelative(0, -10, -1).setTypeId(0);
    ref.getRelative(1, -7, -1).setTypeId(35);
    ref.getRelative(1, -8, -1).setTypeId(0);
    ref.getRelative(1, -9, -1).setTypeId(0);
    ref.getRelative(1, -10, -1).setTypeId(0);
    ref.getRelative(2, -7, -1).setTypeId(35);
    ref.getRelative(2, -8, -1).setTypeId(35);
    ref.getRelative(2, -9, -1).setTypeId(35);
    ref.getRelative(2, -10, -1).setTypeId(35);
    
    return true;
  }
}
