package com.sanjay900.tetris.GAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.json.JSONException;
import org.json.JSONObject;

import com.sanjay900.tetris.Core;
import com.sanjay900.tetris.FIELD.Field;

import lombok.Getter;

@Getter
public class Gamedata
{
	private static Core plugin = Core.getInstance();
	private Game game;
	private int score;
	private int lines;
	private List<GameBlock> gameblock;
	private Scoreboard scoreboard;
	private Objective o;
	boolean gameover = false;
	boolean isinstantlydown = false;
	private int plannedNextblock = (int)Math.floor(Math.random() * 7.0D) + 1;
	private int plannedSecondblock = (int)Math.floor(Math.random() * 7.0D) + 1;
	private int current = 0;
	private boolean beatenHighScore = false;
	private boolean isMoving = false;
	private Field field;
	private Random random;
	private Team tlines;
	private Team tscore;
	private Team[] hs = new Team[5];
	private String[] hsn = new String[5];

	public Gamedata(Field field, Game game)
	{
		this.random = new Random();
		this.field = field;
		this.game = game;
		this.score = -20;
		this.setLines(0);
		this.gameblock = new ArrayList<GameBlock>();
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		o = scoreboard.registerNewObjective("tetris", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"Tetris");
		o.getScore("Current Lines:").setScore(9);
		o.getScore("Current Score:").setScore(8);
		tlines = scoreboard.registerNewTeam("lines");
		tlines.addEntry("Current Lines:");
		tlines.setSuffix(" 0");
		tscore = scoreboard.registerNewTeam("score");
		tscore.addEntry("Current Score:");
		tscore.setSuffix(" 0");
		for (int i = 0; i < 5; i++) {
			String name = "Nobody"+ChatColor.translateAlternateColorCodes('&', "&"+i)+""+ChatColor.RESET;
			hs[i] = scoreboard.registerNewTeam("hs"+i);
			hs[i].setPrefix("HighScore "+(i+1)+":");

			if (plugin.getHighScore().getScore(i) == null) {
				hs[i].setSuffix("0 - 0");

			} else {
				name = plugin.getHighScore().getScore(i).getPlayer();
				hs[i].setSuffix(plugin.getHighScore().getScore(i).getScore()+" - "+plugin.getHighScore().getScore(i).getLines());
			}
			o.getScore(name).setScore(5-(i+1));	
			hs[i].addEntry(name);
			hsn[i] = name;
		}
		Block ZeroBlock = field.lc.getBlock().getRelative(0,1,-1);
		addGameBlocks(ZeroBlock, 12, 19);
		game.getPlayer().setScoreboard(scoreboard);
	}
	public void updateBoard() {
		for (int i = 0; i < 5; i++) {
			String name = "Nobody"+ChatColor.translateAlternateColorCodes('&', "&"+i)+""+ChatColor.RESET;
			if (plugin.getHighScore().getScore(i) == null) {
				hs[i].setSuffix("0 - 0");

			} else {
				name = plugin.getHighScore().getScore(i).getPlayer();
				hs[i].setSuffix(plugin.getHighScore().getScore(i).getScore()+" - "+plugin.getHighScore().getScore(i).getLines());
			}
			if (!name.equals(hsn[i])) {
				scoreboard.resetScores(hsn[i]);
			}
			hsn[i] = name;
			o.getScore(name).setScore(i+1);	
			hs[i].addEntry(name);
		}


	}

	public GameBlock getBoardBlock(int x, int y)
	{
		if ((x < 1) || (y < 1)) {
			return null;
		}
		int xi = x - 1;
		int yi = y - 1;
		int index = yi * 13 + xi;
		if (index >= this.gameblock.size()) {
			return null;
		}
		return (GameBlock)this.gameblock.get(index);
	}

	private void addGameBlocks(Block nullblock, int maxx, int maxy)
	{
		int x = 0;
		int y = 0;
		while (y <= maxy)
		{
			this.gameblock.add(new GameBlock(nullblock.getRelative(0, y, 0 - x)));
			x++;
			if (x > maxx)
			{
				y++;x = 0;
			}
		}
	}

	public boolean newSelectedBlock()
	{
		for (GameBlock b : this.gameblock) {
			b.setSelected(false);
		}
		int x = 1;int y = 1;int newlines = 0;
		while (y <= 20)
		{
			GameBlock b = getBoardBlock(x, y);
			if (b.isSelected())
			{
				y++;x = 1;
			}
			else if (b.getColour() == 15)
			{
				y++;x = 1;
			}
			else if (x >= 11)
			{
				removeLine(y);x = 1;newlines++;
			}
			else
			{
				x++;
			}
		}
		int blockid = this.plannedNextblock;
		this.current = blockid;
		this.plannedNextblock = this.plannedSecondblock;

		this.plannedSecondblock = (random.nextInt(60)+10)/10;

		GameBlock new1 = null;GameBlock new2 = null;GameBlock new3 = null;GameBlock new4 = null;
		byte colour = 1;
		if (blockid == 1)
		{
			new1 = getBoardBlock(6, 19);new2 = getBoardBlock(7, 19);
			new3 = getBoardBlock(6, 20);new4 = getBoardBlock(7, 20);
			colour = 4;
		}
		if (blockid == 2)
		{
			new1 = getBoardBlock(5, 20);new2 = getBoardBlock(7, 20);
			new3 = getBoardBlock(6, 20);new4 = getBoardBlock(8, 20);
			colour = 9;
		}
		if (blockid == 3)
		{
			new1 = getBoardBlock(5, 20);new2 = getBoardBlock(6, 20);
			new3 = getBoardBlock(7, 20);new4 = getBoardBlock(6, 19);
			colour = 10;
		}
		if (blockid == 4)
		{
			new1 = getBoardBlock(6, 20);new2 = getBoardBlock(6, 19);
			new3 = getBoardBlock(7, 19);new4 = getBoardBlock(7, 18);
			colour = 5;
		}
		if (blockid == 5)
		{
			new1 = getBoardBlock(7, 20);new2 = getBoardBlock(7, 19);
			new3 = getBoardBlock(6, 19);new4 = getBoardBlock(6, 18);
			colour = 14;
		}
		if (blockid == 6)
		{
			new1 = getBoardBlock(6, 20);new2 = getBoardBlock(7, 20);
			new3 = getBoardBlock(7, 19);new4 = getBoardBlock(7, 18);
			colour = 1;
		}
		if (blockid == 7)
		{
			new1 = getBoardBlock(7, 20);new2 = getBoardBlock(6, 20);
			new3 = getBoardBlock(6, 19);new4 = getBoardBlock(6, 18);
			colour = 3;
		}
		if ((new1.getColour() != 15) || (new2.getColour() != 15) || (new3.getColour() != 15) || (new4.getColour() != 15))
		{
			gameOver();
			return false;
		}
		new1.setSelected(true);new2.setSelected(true);
		new3.setSelected(true);new4.setSelected(true);

		new1.setColour(colour);new2.setColour(colour);
		new3.setColour(colour);new4.setColour(colour);
		int newscore = getScore() + 20;
		if (newlines == 1) {
			newscore += 1000;
		}
		if (newlines == 2) {
			newscore += 2000;
		}
		if (newlines == 3) {
			newscore += 4000;
		}
		if (newlines == 4) {
			newscore += 8000;
		}
		setScore(newscore);
		if (this.isinstantlydown) {
			this.isinstantlydown = false;
		}
		drawPlannedDrops();
		return true;
	}

	public void moveSelectedLeft()
	{
		setMoving(true);
		boolean canMove = true;
		int x = 1;
		int y = 1;
		while (x <= 12)
		{
			if (!canMove) {
				break;
			}
			if (x >= 2)
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x - 1, y);
				if ((b.isSelected()) && 
						(!nb.isSelected()) && (nb.getColour() != 15)) {
					canMove = false;
				}
			}
			if (x == 1)
			{
				GameBlock b = getBoardBlock(x, y);
				if (b.isSelected()) {
					canMove = false;
				}
			}
			y++;
			if (y > 20)
			{
				y = 1;x++;
			}
		}
		if (!canMove) {
			return;
		}
		x = 2;
		y = 1;
		while (x <= 12)
		{
			if (getBoardBlock(x, y) != null)
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x - 1, y);
				if (b.isSelected())
				{
					nb.setSelected(true);
					nb.setColour(b.getColour());
					b.setSelected(false);
					b.setColour((byte)15);
				}
			}
			y++;
			if (y > 20)
			{
				y = 1;x++;
			}
		}
		Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable(){

			@Override
			public void run() {
				setMoving(false);

			}}, 2L);

	}

	public void moveSelectedRight()
	{
		setMoving(true);
		boolean canMove = true;
		int x = 12;
		int y = 1;
		while (x >= 1)
		{
			if (!canMove) {
				break;
			}
			if (x <= 10)
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x + 1, y);
				if ((b.isSelected()) && 
						(!nb.isSelected()) && (nb.getColour() != 15)) {
					canMove = false;
				}
			}
			if (x == 11)
			{
				GameBlock b = getBoardBlock(x, y);
				if (b.isSelected()) {
					canMove = false;
				}
			}
			y++;
			if (y > 20)
			{
				y = 1;x--;
			}
		}
		if (!canMove) {
			return;
		}
		x = 12;
		y = 1;
		while (x >= 1)
		{
			if (getBoardBlock(x, y) != null)
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x + 1, y);
				if (b.isSelected())
				{
					nb.setSelected(true);
					nb.setColour(b.getColour());
					b.setSelected(false);
					b.setColour((byte)15);
				}
			}
			y++;
			if (y > 20)
			{
				y = 1;x--;
			}
		}
		Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable(){

			@Override
			public void run() {
				setMoving(false);

			}}, 2L);
	}

	public boolean moveSelectedDown()
	{
		setMoving(true);
		boolean canMove = true;
		int x = 1;
		int y = 1;
		while (y <= 20)
		{
			if (!canMove) {
				break;
			}
			if (y == 1)
			{
				GameBlock b = getBoardBlock(x, y);
				if (b.isSelected()) {
					canMove = false;
				}
			}
			else
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x, y - 1);
				if ((b.isSelected()) && 
						(!nb.isSelected()) && (nb.getColour() != 15)) {
					canMove = false;
				}
			}
			x++;
			if (x > 12)
			{
				x = 1;y++;
			}
		}
		if (!canMove)
		{
			newSelectedBlock();return false;
		}
		x = 1;
		y = 1;
		while (y <= 20)
		{
			if (getBoardBlock(x, y) != null)
			{
				GameBlock b = getBoardBlock(x, y);
				GameBlock nb = getBoardBlock(x, y - 1);
				if (b.isSelected())
				{
					nb.setSelected(true);
					nb.setColour(b.getColour());
					b.setSelected(false);
					b.setColour((byte)15);
				}
			}
			x++;
			if (x > 12)
			{
				x = 1;y++;
			}
		}
		Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable(){

			@Override
			public void run() {
				setMoving(false);

			}}, 2L);
		return true;
	}

	public void rotateSelected()
	{
		int xmin = 12;int xmax = 0;int ymin = 16;int ymax = 0;
		int x = 1;int y = 1;
		while (y <= 20)
		{
			GameBlock b = getBoardBlock(x, y);
			if (b.isSelected())
			{
				if (x < xmin) {
					xmin = x;
				}
				if (x > xmax) {
					xmax = x;
				}
				if (y < ymin) {
					ymin = y;
				}
				if (y > ymax) {
					ymax = y;
				}
			}
			x++;
			if (x > 12)
			{
				x = 1;y++;
			}
		}
		int xmid = 0;int ymid = 0;
		xmid = Math.round((xmax - xmin) / 2);
		ymid = Math.round((ymax - ymin) / 2);

		ymid += ymin;xmid += xmin;

		x = 1;y = 1;
		List<GameBlock> changed = new ArrayList<GameBlock>();
		int xdif;
		while (y <= 20)
		{
			GameBlock b = getBoardBlock(x, y);
			if (b.isSelected())
			{
				int ydif = y - ymid;
				xdif = x - xmid;
				int yp = 0 - xdif;int xp = ydif;
				GameBlock nb = getBoardBlock(xp + xmid, yp + ymid);
				if (yp + ymid > 20)
				{
					undoPlans();return;
				}
				if ((xp + xmid > 12) || (xp + xmid < 0))
				{
					undoPlans();return;
				}
				if (nb == null)
				{
					undoPlans();return;
				}
				if ((!nb.isSelected()) && (nb.getColour() != 15))
				{
					undoPlans();return;
				}
				if (!b.isPlannedSelected())
				{
					b.setPlannedColour((byte)15);
					b.setPlannedSelected(false);
					changed.add(b);
				}
				nb.setPlannedColour(b.getColour());
				nb.setPlannedSelected(true);
				changed.add(nb);
			}
			x++;
			if (x > 12)
			{
				x = 1;y++;
			}
		}
		for (GameBlock cur : changed) {
			if ((cur.getColour() != cur.getPlannedColour()) || (cur.isPlannedSelected())) {
				cur.executePlans();
			}
		}
	}

	public void undoPlans()
	{
		for (GameBlock cur : this.gameblock)
		{
			cur.setPlannedColour(cur.getColour());
			cur.setPlannedSelected(false);
		}
	}

	public void removeLine(int yline)
	{
		int x = 1;int y = yline + 1;
		while (y <= 20)
		{
			GameBlock b = getBoardBlock(x, y);
			GameBlock nb = getBoardBlock(x, y - 1);
			if ((!b.isSelected()) && (!nb.isSelected()))
			{
				nb.setColour(b.getColour());
				nb.setSelected(false);
				b.setColour((byte)15);
				b.setSelected(false);
			}
			x++;
			if (x > 11)
			{
				x = 1;y++;
			}
		}
		setScore(getScore() + 1000);
		this.setLines(this.getLines() + 1);
	}

	public int getScore()
	{
		return this.score;
	}

	public void setScore(int score)
	{
		this.score = score;
		updateSocket();
		if ((score > plugin.getHighScore().getHighScore().getScore()) && (!this.beatenHighScore))
		{
			//TODO: Broadcast across servers.
			this.beatenHighScore = true;
			Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TETRIS" + ChatColor.WHITE + "] Player " + ChatColor.GREEN + this.game.getPlayer().getName() + ChatColor.WHITE + " is beating the highscore atm!");

		}
		tlines.setSuffix(" "+lines);
		tscore.setSuffix(" "+score);

	}
	public void gameOver()
	{

		updateSocket();
		this.gameover = true;
		plugin.getHighScore().postHighscore(this.game);

	}

	public boolean isGameOver()
	{
		return this.gameover;
	}

	public boolean isMovingInstantlyDown()
	{
		return this.isinstantlydown;
	}

	public void moveInstantlyDown()
	{
		this.isinstantlydown = true;
	}

	@SuppressWarnings("deprecation")
	public void drawPlannedDrops()
	{
		Block curzero = this.game.field.lc.getBlock().getRelative(0, 15, 5);
		Block firstzero = this.game.field.lc.getBlock().getRelative(0, 7, 5);
		Block secondzero = this.game.field.lc.getBlock().getRelative(0, 2, 5);
		byte c1 = 15;byte c2 = 15;byte c3 = 15;byte c4 = 15;byte c5 = 15;byte c6 = 15;byte c7 = 15;byte c8 = 15;
		byte f1 = 15;byte f2 = 15;byte f3 = 15;byte f4 = 15;byte f5 = 15;byte f6 = 15;byte f7 = 15;byte f8 = 15;
		byte s1 = 15;byte s2 = 15;byte s3 = 15;byte s4 = 15;byte s5 = 15;byte s6 = 15;byte s7 = 15;byte s8 = 15;
		if (this.current == 1)
		{

			c2 = 4;c3 = 4;c6 = 4;c7 = 4;
		}
		if (this.current == 2)
		{
			c1 = 9;c2 = 9;c3 = 9;c4 = 9;
		}
		if (this.current == 3)
		{
			c2 = 10;c5 = 10;c6 = 10;c7 = 10;
		}
		if (this.current == 4)
		{
			c1 = 5;c2 = 5;c6 = 5;c7 = 5;
		}
		if (this.current == 5)
		{
			c2 = 14;c3 = 14;c5 = 14;c6 = 14;
		}
		if (this.current == 6)
		{
			c1 = 1;c2 = 1;c3 = 1;c7 = 1;
		}
		if (this.current == 7)
		{
			c3 = 3;c5 = 3;c6 = 3;c7 = 3;
		}




		if (this.plannedNextblock == 1)
		{
			f2 = 4;f3 = 4;f6 = 4;f7 = 4;
		}
		if (this.plannedNextblock == 2)
		{
			f1 = 9;f2 = 9;f3 = 9;f4 = 9;
		}
		if (this.plannedNextblock == 3)
		{
			f2 = 10;f5 = 10;f6 = 10;f7 = 10;
		}
		if (this.plannedNextblock == 4)
		{
			f1 = 5;f2 = 5;f6 = 5;f7 = 5;
		}
		if (this.plannedNextblock == 5)
		{
			f2 = 14;f3 = 14;f5 = 14;f6 = 14;
		}
		if (this.plannedNextblock == 6)
		{
			f1 = 1;f2 = 1;f3 = 1;f7 = 1;
		}
		if (this.plannedNextblock == 7)
		{
			f3 = 3;f5 = 3;f6 = 3;f7 = 3;
		}
		if (this.plannedSecondblock == 1)
		{
			s2 = 4;s3 = 4;s5 = 4;s6 = 4;
		}
		if (this.plannedSecondblock == 2)
		{
			s1 = 9;s2 = 9;s3 = 9;s4 = 9;
		}
		if (this.plannedSecondblock == 3)
		{
			s2 = 10;s5 = 10;s6 = 10;s7 = 10;
		}
		if (this.plannedSecondblock == 4)
		{
			s1 = 5;s2 = 5;s6 = 5;s7 = 5;
		}
		if (this.plannedSecondblock == 5)
		{
			s2 = 14;s3 = 14;s5 = 14;s6 = 14;
		}
		if (this.plannedSecondblock == 6)
		{
			s1 = 1;s2 = 1;s3 = 1;s7 = 1;
		}
		if (this.plannedSecondblock == 7)
		{
			s3 = 3;s5 = 3;s6 = 3;s7 = 3;
		}
		firstzero.getRelative(0, 0, 0).setTypeIdAndData(35, f1, true);
		firstzero.getRelative(0, 1, 0).setTypeIdAndData(35, f2, true);
		firstzero.getRelative(0, 2, 0).setTypeIdAndData(35, f3, true);
		firstzero.getRelative(0, 3, 0).setTypeIdAndData(35, f4, true);
		firstzero.getRelative(0, 0, 1).setTypeIdAndData(35, f5, true);
		firstzero.getRelative(0, 1, 1).setTypeIdAndData(35, f6, true);
		firstzero.getRelative(0, 2, 1).setTypeIdAndData(35, f7, true);
		firstzero.getRelative(0, 3, 1).setTypeIdAndData(35, f8, true);
		curzero.getRelative(0, 0, 0).setTypeIdAndData(35, c1, true);
		curzero.getRelative(0, 1, 0).setTypeIdAndData(35, c2, true);
		curzero.getRelative(0, 2, 0).setTypeIdAndData(35, c3, true);
		curzero.getRelative(0, 3, 0).setTypeIdAndData(35, c4, true);
		curzero.getRelative(0, 0, 1).setTypeIdAndData(35, c5, true);
		curzero.getRelative(0, 1, 1).setTypeIdAndData(35, c6, true);
		curzero.getRelative(0, 2, 1).setTypeIdAndData(35, c7, true);
		curzero.getRelative(0, 3, 1).setTypeIdAndData(35, c8, true);
		secondzero.getRelative(0, 0, 0).setTypeIdAndData(35, s1, true);
		secondzero.getRelative(0, 1, 0).setTypeIdAndData(35, s2, true);
		secondzero.getRelative(0, 2, 0).setTypeIdAndData(35, s3, true);
		secondzero.getRelative(0, 3, 0).setTypeIdAndData(35, s4, true);
		secondzero.getRelative(0, 0, 1).setTypeIdAndData(35, s5, true);
		secondzero.getRelative(0, 1, 1).setTypeIdAndData(35, s6, true);
		secondzero.getRelative(0, 2, 1).setTypeIdAndData(35, s7, true);
		secondzero.getRelative(0, 3, 1).setTypeIdAndData(35, s8, true);
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		updateSocket();
		this.lines = lines;
	}
	public void updateSocket() {
		if (gameblock == null) return;
		ArrayList<Byte> colours = new ArrayList<>();
		for (GameBlock b : gameblock) {
			colours.add(b.getColour());
		}
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", plugin.getServerid());
			obj.put("blocks", colours.toArray());
			obj.put("score", getScore());
			obj.put("player", game.getPlayer().getName());
			obj.put("uuid", game.getPlayer().getUniqueId().toString());
			obj.put("lines", getLines());
			obj.put("nextPiece", plannedNextblock);
			obj.put("nextPiece2",  plannedSecondblock);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			Core.getInstance().client.sendGlobalMessage("tetris", obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}
}
