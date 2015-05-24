package net.gamesketch.bukkit.tetris.FIELD;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Oldblock
{
  int x;
  int y;
  int z;
  World world;
  Material material;
  byte data;
  int blocktype = 0;
  String[] signLines;
  ItemStack[] chestInventory;
  EntityType spawningType;
  int spawnDelay;
  
  @SuppressWarnings("deprecation")
public Oldblock(Block b)
  {
    this.x = b.getX();
    this.y = b.getY();
    this.z = b.getZ();
    this.world = b.getWorld();
    this.material = b.getType();
    this.data = b.getData();
    if ((b.getState() instanceof Sign))
    {
      Sign sign = (Sign)b.getState();
      this.blocktype = 1;
      this.signLines = sign.getLines();
    }
    if ((b.getState() instanceof InventoryHolder))
    {
      InventoryHolder container = (InventoryHolder)b.getState();
      this.blocktype = 2;
      this.chestInventory = ((ItemStack[])container.getInventory().getContents().clone());
      
      container.getInventory().clear();
    }
    if ((b.getState() instanceof CreatureSpawner))
    {
      CreatureSpawner spawner = (CreatureSpawner)b.getState();
      this.blocktype = 3;
      this.spawningType = spawner.getSpawnedType();
      this.spawnDelay = spawner.getDelay();
    }
    int id = b.getTypeId();
    if ((id == 6) || ((id >= 26) && (id <= 28)) || ((id >= 37) && (id <= 40)) || (id == 50) || (id == 55) || ((id >= 63) && (id <= 66)) || 
      ((id >= 68) && (id <= 72)) || ((id >= 75) && (id <= 77)) || (id == 81) || (id == 83) || (id == 93) || (id == 94)) {
      b.setTypeId(0);
    }
  }
  
  @SuppressWarnings("deprecation")
public boolean restore()
  {
    Block cur = this.world.getBlockAt(this.x, this.y, this.z);
    cur.setType(this.material);
    cur.setData(this.data);
    try
    {
      if (this.blocktype == 1)
      {
        Sign cursign = (Sign)cur.getState();
        cursign.setLine(0, this.signLines[0]);
        cursign.setLine(1, this.signLines[1]);
        cursign.setLine(2, this.signLines[2]);
        cursign.setLine(3, this.signLines[3]);
        cursign.update();
      }
      if (this.blocktype == 2) {
        ((InventoryHolder)cur.getState()).getInventory().setContents(this.chestInventory);
      }
      if (this.blocktype == 3)
      {
        ((CreatureSpawner)cur.getState()).setDelay(this.spawnDelay);
        ((CreatureSpawner)cur.getState()).setSpawnedType(this.spawningType);
      }
    }
    catch (ClassCastException e)
    {
      System.out.println("Failed to restore an block after tetris");
    }
    return true;
  }
}
