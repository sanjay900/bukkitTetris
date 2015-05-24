package net.gamesketch.bukkit.tetris.GAME;

import java.awt.image.BufferedImage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ScoreMapRenderer extends MapRenderer {
		private boolean redrawNeeded = true;
		private int x = 0;
		private int y = 0;
		private BufferedImage image;
		private BufferedImage imageOld;
	  public ScoreMapRenderer(int x, int y, BufferedImage image) {
			this.x = x * 128;
			this.y = y * 128;
			recalculateInput(image);
		}
	  public void recalculateInput(BufferedImage input)
	    {
	        int x2 = 128;
	        int y2 = 128;
	        
	        this.image = input.getSubimage(x, y, x2, y2);

	    }
	public void render(MapView map, MapCanvas canvas, Player player) {
		  	 if (redrawNeeded) {
		  		 
		        canvas.drawImage(0, 0, image);
		        this.imageOld = image;
		  	 redrawNeeded = false;
		  	 for (Player p : Bukkit.getOnlinePlayers()) {
		  	p.sendMap(map);
		  	 }
		  	 }
		  }
		
	public void setScore(BufferedImage image) {
		recalculateInput(image);
		boolean hasC = false;
		 for ( int x = 0; x < 128; x++ ) {
	            for ( int y = 0; y < 128; y++ ){
	                int p1 = this.image.getRGB( x, y );
	                int p2 = imageOld.getRGB( x, y );
	                if ( p1 != p2 ) {
	                   hasC = true;
	                }
	            }
	        }
		redrawNeeded = hasC;
	}
}
