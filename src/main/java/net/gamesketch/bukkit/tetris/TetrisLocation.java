package net.gamesketch.bukkit.tetris;

import org.bukkit.World;
import org.bukkit.block.Block;

public class TetrisLocation
{
  int x;
  int y;
  int z;
  World world;
  private int id;
  
  public TetrisLocation(int id, Block b)
  {
    this.x = b.getX();
    this.y = b.getY();
    this.z = b.getZ();
    this.world = b.getWorld();
    this.setId(id);
  }
  
  public TetrisLocation(int id, World w, int x, int y, int z)
  {
	this.setId(id);
    this.x = x;
    this.y = y;
    this.z = z;
    this.world = w;
  }
  
  public Block getBlock()
  {
    return this.world.getBlockAt(this.x, this.y, this.z);
  }
  
  public String toString()
  {
    return this.id+":"+this.x + ":" + this.y + ":" + this.z + ":" + this.world.getName();
  }
  
  public boolean compare(Block b)
  {
    if (b.getX() != this.x) {
      return false;
    }
    if (b.getY() != this.y) {
      return false;
    }
    if (b.getZ() != this.z) {
      return false;
    }
    return b.getWorld().getName() == this.world.getName();
  }

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
}
