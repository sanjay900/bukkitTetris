package net.gamesketch.bukkit.tetris.GAME;

import net.gamesketch.bukkit.tetris.FIELD.Field;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class GameButtons
{
  Block left1;
  Block left2;
  Block left3;
  Block right1;
  Block right2;
  Block right3;
  Block up1;
  Block up2;
  Block up3;
  Block up4;
  Block down1;
  Block down2;
  Block down3;
  Block down4;
  
  public GameButtons(Field field)
  {
    Block ref = field.lc.getBlock();
    
















    this.left1 = ref.getRelative(6, 11, 4);
    this.left2 = this.left1.getRelative(BlockFace.UP);
    this.left3 = this.left2.getRelative(BlockFace.UP);
    this.down1 = this.left1.getRelative(BlockFace.DOWN);
    if (this.down1.getRelative(BlockFace.EAST).isEmpty())
    {
      this.left1 = ref.getRelative(6, 11, 4);
      this.down2 = this.down1.getRelative(BlockFace.SOUTH);
      this.down3 = this.down2.getRelative(BlockFace.SOUTH);
      this.down4 = this.down3.getRelative(BlockFace.SOUTH);
      this.up1 = this.left3.getRelative(BlockFace.UP);
      this.up2 = this.up1.getRelative(BlockFace.SOUTH);
      this.up3 = this.up2.getRelative(BlockFace.SOUTH);
      this.up4 = this.up3.getRelative(BlockFace.SOUTH);
      this.right3 = this.up4.getRelative(BlockFace.DOWN);
      this.right2 = this.right3.getRelative(BlockFace.DOWN);
      this.right1 = this.right2.getRelative(BlockFace.DOWN);
    }
    else
    {
      this.down2 = this.down1.getRelative(BlockFace.EAST);
      this.down3 = this.down2.getRelative(BlockFace.EAST);
      this.down4 = this.down3.getRelative(BlockFace.EAST);
      this.up1 = this.left3.getRelative(BlockFace.UP);
      this.up2 = this.up1.getRelative(BlockFace.EAST);
      this.up3 = this.up2.getRelative(BlockFace.EAST);
      this.up4 = this.up3.getRelative(BlockFace.EAST);
      this.right3 = this.up4.getRelative(BlockFace.DOWN);
      this.right2 = this.right3.getRelative(BlockFace.DOWN);
      this.right1 = this.right2.getRelative(BlockFace.DOWN);
    }
  }
  
  public BlockFace getClickedDirection(Block c)
  {
    if ((equals(c, this.left1)) || (equals(c, this.left2)) || (equals(c, this.left3))) {
      return BlockFace.EAST;
    }
    if ((equals(c, this.right1)) || (equals(c, this.right2)) || (equals(c, this.right3))) {
      return BlockFace.WEST;
    }
    if ((equals(c, this.up1)) || (equals(c, this.up2)) || (equals(c, this.up3)) || (equals(c, this.up4))) {
      return BlockFace.NORTH;
    }
    if ((equals(c, this.down1)) || (equals(c, this.down2)) || (equals(c, this.down3)) || (equals(c, this.down4))) {
      return BlockFace.SOUTH;
    }
    if (equals(c, this.left1.getRelative(-1, -1, -3))) {
      return BlockFace.SOUTH_WEST;
    }
    return BlockFace.SELF;
  }
  
  private boolean equals(Block a, Block b)
  {
    if (a.getX() != b.getX()) {
      return false;
    }
    if (a.getY() != b.getY()) {
      return false;
    }
    if (a.getZ() != b.getZ()) {
      return false;
    }
    return a.getWorld() == b.getWorld();
  }
}
