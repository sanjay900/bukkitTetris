package net.gamesketch.bukkit.tetris.FIELD;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class GameBounds
{
  int x1;
  int y1;
  int z1;
  int x2;
  int y2;
  int z2;
  World world;
  Location lc;
  public GameBounds(Location lc, Location uc) {
	  this.x1 = (lc.getBlockX());
	    this.y1 = (lc.getBlockY());
	    this.z1 = (lc.getBlockZ());
	    this.x2 = (uc.getBlockX());
	    this.y2 = (uc.getBlockY());
	    this.z2 = (uc.getBlockZ());
	    this.lc = lc;
	    
	    this.world = lc.getWorld();
}

@SuppressWarnings("deprecation")
public boolean construct()
  {
    int z = this.z1;
    int y = this.y1;
    while (y <= this.y2)
    {
      Block cur = this.world.getBlockAt(this.x1, y, z);
      cur.setType(Material.WOOL);
      if ((z == this.z2) || (z == this.z1) || (y == this.y2) || (y == this.y1)) {
        cur.setData((byte)8);
      } else {
        cur.setData((byte)15);
      }
      z--;
      if (z < this.z2)
      {
        z = this.z1;y++;
      }
    }
    Block curzero = lc.getBlock().getRelative(0, 15, 5);
    Block firstzero = lc.getBlock().getRelative(0, 7, 5);
    Block secondzero = lc.getBlock().getRelative(0, 2, 5);
	firstzero.getRelative(0, 0, 0).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 1, 0).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 2, 0).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 3, 0).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 0, 1).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 1, 1).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 2, 1).setTypeIdAndData(35, (byte)15, true);
	firstzero.getRelative(0, 3, 1).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 0, 0).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 1, 0).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 2, 0).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 3, 0).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 0, 1).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 1, 1).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 2, 1).setTypeIdAndData(35, (byte)15, true);
	curzero.getRelative(0, 3, 1).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 0, 0).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 1, 0).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 2, 0).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 3, 0).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 0, 1).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 1, 1).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 2, 1).setTypeIdAndData(35, (byte)15, true);
	secondzero.getRelative(0, 3, 1).setTypeIdAndData(35, (byte)15, true);
    return true;
  }

}
