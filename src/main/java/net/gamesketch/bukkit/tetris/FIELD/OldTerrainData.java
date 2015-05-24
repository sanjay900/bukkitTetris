package net.gamesketch.bukkit.tetris.FIELD;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;

public class OldTerrainData
{
  List<Oldblock> list;
  
  public OldTerrainData(Walls walls)
  {
    this.list = new ArrayList<>();
    
    Block ref1 = walls.getCorner1();
    Block ref2 = walls.getCorner2();
    
    int minx = ref1.getX();
    int miny = ref1.getY();
    int minz = ref1.getZ();
    
    int maxx = ref2.getX();
    int maxy = ref2.getY();
    int maxz = ref2.getZ();
    
    int x = minx;
    int y = miny;
    int z = minz;
    while (z <= maxz)
    {
      this.list.add(new Oldblock(ref1.getWorld().getBlockAt(x, y, z)));
      
      x++;
      if (x > maxx)
      {
        x = minx;y++;
      }
      if (y > maxy)
      {
        y = miny;z++;
      }
    }
  }
  
  public boolean restore()
  {
    for (Oldblock cur : this.list) {
      cur.restore();
    }
    return true;
  }
}
