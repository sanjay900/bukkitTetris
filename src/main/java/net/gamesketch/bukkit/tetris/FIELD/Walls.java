package net.gamesketch.bukkit.tetris.FIELD;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Walls
{
  int x1;
  int y1;
  int z1;
  int x2;
  int y2;
  int z2;
  World world;
  
  public Walls(Location loc)
  {
    Block ref = loc.getBlock();
    this.x1 = (ref.getX() - 7);
    this.y1 = (ref.getY() - 11);
    this.z1 = (ref.getZ() - 1);
    this.x2 = (ref.getX() + 8);
    this.y2 = (ref.getY() + 8);
    this.z2 = (ref.getZ() + 18);
    this.world = loc.getWorld();
  }
  
  public Block getCorner1()
  {
    return this.world.getBlockAt(this.x1, this.y1, this.z1);
  }
  
  public Block getCorner2()
  {
    return this.world.getBlockAt(this.x2, this.y2, this.z2);
  }
  
  public boolean isOutsideBounds()
  {
    return (this.y1 <= 1) || (this.y1 >= 127) || (this.y2 <= 1) || (this.y2 >= 127);
  }
  
  public boolean construct()
  {
    int x = this.x1;
    int y = this.y1;
    int z = this.z1;
    while (z <= this.z2)
    {
      Block cur = this.world.getBlockAt(x, y, z);
      cur.getTypeId();
      if ((x == this.x1) || (x == this.x2) || (y == this.y1) || (y == this.y2) || (z == this.z1) || (z == this.z2)) {
        cur.setType(Material.GLASS);
      } else {
        cur.setType(Material.AIR);
      }
      x++;
      if (x > this.x2)
      {
        x = this.x1;y++;
      }
      if (y > this.y2)
      {
        y = this.y1;z++;
      }
    }
    return true;
  }
}
