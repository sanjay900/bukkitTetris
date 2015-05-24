package net.gamesketch.bukkit.tetris.GAME;

import org.bukkit.block.Block;

public class GameBlock
{
  Block block;
  boolean selected;
  byte colour;
  boolean plannedSelected;
  byte plannedcolour;
  
  public GameBlock(Block b)
  {
    this.block = b;
    this.selected = false;
    this.colour = 15;
    this.plannedSelected = false;
    this.plannedcolour = 15;
  }
  
  public Block getBlock()
  {
    return this.block;
  }
  
  public boolean isSelected()
  {
    return this.selected;
  }
  
  public byte getColour()
  {
    return this.colour;
  }
  
  public void setSelected(boolean selected)
  {
    this.selected = selected;
  }
  
  public void setColour(byte colour)
  {
    this.colour = colour;
    this.block.setTypeIdAndData(35, colour, true);
  }
 
  public void setRed()
  {
	  this.colour = 14;
    this.block.setTypeIdAndData(159, (byte)14, true);
  }
  
  public void setPlannedColour(byte colour)
  {
    this.plannedcolour = colour;
  }
  
  public void setPlannedSelected(boolean selected)
  {
    this.plannedSelected = selected;
  }
  
  public void executePlans()
  {
    setSelected(this.plannedSelected);
    setColour(this.plannedcolour);
    this.plannedSelected = false;
    this.plannedcolour = this.colour;
  }
  
  public boolean isPlannedSelected()
  {
    return this.plannedSelected;
  }
  
  public byte getPlannedColour()
  {
    return this.plannedcolour;
  }
}
