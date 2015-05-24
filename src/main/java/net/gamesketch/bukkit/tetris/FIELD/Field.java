package net.gamesketch.bukkit.tetris.FIELD;

import org.bukkit.Location;

public class Field
{
  GameBounds bounds;
  public Location lc;
  public Location uc;
  public Field(Location loc)
  {
	  this.lc = loc.getBlock().getLocation().clone().add(-26,2,4);
	  this.uc = loc.getBlock().getLocation().clone().add(-26,23,-8);
    this.bounds = new GameBounds(lc, uc);
  }
  
  public boolean restore()
  {
	  this.bounds.construct();
	return true;
	  
  }
  
  public boolean construct()
  {
      return this.bounds.construct();
    
  }
  
  
}
