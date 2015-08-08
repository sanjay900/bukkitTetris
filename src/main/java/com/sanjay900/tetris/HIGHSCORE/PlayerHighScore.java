package com.sanjay900.tetris.HIGHSCORE;

import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;
@Getter
public class PlayerHighScore
{
	String player;
	int score;
	int lines;
	UUID uuid;

	public PlayerHighScore(Player p, int score, int lines)
	{
		this(p.getName(),p.getUniqueId(),score,lines);
	}

	public PlayerHighScore(String player, UUID uuid, int score, int lines)
	{
		this.player = player;
		this.score = score;
		this.uuid = uuid;
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		return getPlayer() + " - " + getScore() +" - Lines Cleared: "+getLines();
	}
}
