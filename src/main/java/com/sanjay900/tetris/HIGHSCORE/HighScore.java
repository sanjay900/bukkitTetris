package com.sanjay900.tetris.HIGHSCORE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.sanjay900.tetris.Core;
import com.sanjay900.tetris.GAME.Game;
import com.sanjay900.tetris.SETTINGS.SetScore;

public class HighScore
{
	public HashMap<UUID,PlayerHighScore> scoresv = new HashMap<>();
	public List<PlayerHighScore> scores = new ArrayList<>();
	public void sortHighScore() {
		scores.clear();
		scores.addAll(scoresv.values());
		Collections.sort(scores, new Comparator<PlayerHighScore>() {
		    @Override
		    public int compare(PlayerHighScore z1, PlayerHighScore z2) {
		        if (z1.score > z2.score)
		            return 1;
		        if (z1.score < z2.score)
		            return -1;
		        return 0;
		    }
		});
		Core.getInstance().getGame().data().updateBoard();
	}

	public void postHighscore(Game game) {
		Player p = game.getPlayer();
		PlayerHighScore hs = new PlayerHighScore(p,game.data().getScore(),game.data().getLines());
		Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), new SetScore(hs));
		scoresv.put(p.getUniqueId(), hs);
		sortHighScore();
		p.sendMessage(ChatColor.GREEN + "==========" + ChatColor.RED + "Game over!" + ChatColor.GREEN + "===========");
		p.sendMessage(ChatColor.GREEN + "=======" + ChatColor.LIGHT_PURPLE + "Scoreboard:" + ChatColor.GREEN + "======");
		p.sendMessage(ChatColor.GREEN + "=" + ChatColor.YELLOW + "First:  " + scores.get(0));
		if (scores.size() > 1)
		p.sendMessage(ChatColor.GREEN + "=" + ChatColor.WHITE + "Second: " + scores.get(1));
		if (scores.size() > 2)
		p.sendMessage(ChatColor.GREEN + "=" + ChatColor.RED + "Third:  " + scores.get(2));
		if (scores.size() > 3)
		p.sendMessage(ChatColor.GREEN + "=" + ChatColor.GRAY + "Fourth: " + scores.get(3));
		if (scores.size() > 4)
		p.sendMessage(ChatColor.GREEN + "=" + ChatColor.GRAY + "Fifth:  " + scores.get(4));
		p.sendMessage(ChatColor.GREEN + "==============================");
	}

	public PlayerHighScore getHighScore() {
		return scores.get(0);
	}

	public void setScore(UUID u, PlayerHighScore hs) {
		scoresv.put(u, hs);
		sortHighScore();
	}

	public PlayerHighScore getScore(int i) {
		if (scores.size() < i+1) return null;
		return scores.get(i);
	}
  
  
}
