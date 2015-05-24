package net.gamesketch.bukkit.tetris.GAME;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import net.gamesketch.bukkit.tetris.Core;
import net.gamesketch.bukkit.tetris.SETTINGS.Settings;

import org.bukkit.Bukkit;

import com.sanjay900.nmsUtil.sk89q.jinglenote.MelodyPlayer;
import com.sanjay900.nmsUtil.sk89q.jinglenote.MidiJingleSequencer;

public class GameLoop
  implements Runnable
{
  Game game;
  int ticks;
  long totalticks;
  boolean isGameStarted;
  int gameOverCounterLine = 1;
  int timeoutTicks = -1;
  int musicTicks = 0;
  int gameSpeed;
  boolean isMusicEnabled = true;
  boolean midiPlaying = false;
  boolean isLighted = false;
  MelodyPlayer p;
  public GameLoop(Game game)
  {
    this.game = game;
    this.ticks = 0;
    this.totalticks = 0L;
    this.isGameStarted = false;
    this.gameSpeed = 5;
  }
  
  public void run()
  {
	  game.data().updateSocket();
    this.ticks += 1;
    this.totalticks += 1L;
    if (this.timeoutTicks >= 0)
    {
      this.timeoutTicks -= 1;
      if (this.timeoutTicks == -1)
      {
    	  
        Core.getLocalPlayer(this.game.getPlayer()).setGame(null);
        this.game.cancelGameloop();
      }
    }
    if (!this.isGameStarted)
    { 
    	if (!this.midiPlaying) {
    	String midiName="tetris.mid";
    	File[] trialPaths = {
            new File(Core.cplugin.getDataFolder().getPath()+"/midi/", midiName)
    };
    	File file = null;
    for (File f : trialPaths) {
        if (f.exists()) {
            file = f;
            break;
        }
    }

    if(file != null && file.exists()) {
    	try {
			p = new MelodyPlayer(new MidiJingleSequencer(file, true));
			p.play(this.game.getPlayer().getName());
			Bukkit.getScheduler().runTaskAsynchronously(Core.cplugin, p);
		} catch (MidiUnavailableException e) {
			// 
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// 
			e.printStackTrace();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
    }
    midiPlaying = true;
    	}
      if (!Settings.countdownOnStartup)
      {
        this.isGameStarted = true;
        this.totalticks = 0L;
        this.game.data().newSelectedBlock();
        return;
      }
      if (this.totalticks >= 45L)
      {
        this.game.data().getBoardBlock(5, 11).setColour((byte)15);
        this.game.data().getBoardBlock(6, 11).setColour((byte)15);
        this.game.data().getBoardBlock(7, 11).setColour((byte)15);
        this.game.data().getBoardBlock(5, 10).setColour((byte)15);
        this.game.data().getBoardBlock(6, 10).setColour((byte)15);
        this.game.data().getBoardBlock(7, 10).setColour((byte)15);
        this.game.data().getBoardBlock(5, 9).setColour((byte)15);
        this.game.data().getBoardBlock(6, 9).setColour((byte)15);
        this.game.data().getBoardBlock(7, 9).setColour((byte)15);
        this.game.data().getBoardBlock(5, 8).setColour((byte)15);
        this.game.data().getBoardBlock(6, 8).setColour((byte)15);
        this.game.data().getBoardBlock(7, 8).setColour((byte)15);
        this.game.data().getBoardBlock(5, 7).setColour((byte)15);
        this.game.data().getBoardBlock(6, 7).setColour((byte)15);
        this.game.data().getBoardBlock(7, 7).setColour((byte)15);
        this.isGameStarted = true;
        this.totalticks = 0L;
        this.game.data().newSelectedBlock();
      }
      else if (this.totalticks >= 30L)
      {
        this.game.data().getBoardBlock(5, 11).setColour((byte)0);
        this.game.data().getBoardBlock(6, 11).setColour((byte)0);
        this.game.data().getBoardBlock(7, 11).setColour((byte)15);
        this.game.data().getBoardBlock(5, 10).setColour((byte)15);
        this.game.data().getBoardBlock(6, 10).setColour((byte)0);
        this.game.data().getBoardBlock(7, 10).setColour((byte)15);
        this.game.data().getBoardBlock(5, 9).setColour((byte)15);
        this.game.data().getBoardBlock(6, 9).setColour((byte)0);
        this.game.data().getBoardBlock(7, 9).setColour((byte)15);
        this.game.data().getBoardBlock(5, 8).setColour((byte)15);
        this.game.data().getBoardBlock(6, 8).setColour((byte)0);
        this.game.data().getBoardBlock(7, 8).setColour((byte)15);
        this.game.data().getBoardBlock(5, 7).setColour((byte)0);
        this.game.data().getBoardBlock(6, 7).setColour((byte)0);
        this.game.data().getBoardBlock(7, 7).setColour((byte)0);
      }
      else if (this.totalticks >= 15L)
      {
        this.game.data().getBoardBlock(5, 11).setColour((byte)0);
        this.game.data().getBoardBlock(6, 11).setColour((byte)0);
        this.game.data().getBoardBlock(7, 11).setColour((byte)0);
        this.game.data().getBoardBlock(5, 10).setColour((byte)15);
        this.game.data().getBoardBlock(6, 10).setColour((byte)15);
        this.game.data().getBoardBlock(7, 10).setColour((byte)0);
        this.game.data().getBoardBlock(5, 9).setColour((byte)0);
        this.game.data().getBoardBlock(6, 9).setColour((byte)0);
        this.game.data().getBoardBlock(7, 9).setColour((byte)0);
        this.game.data().getBoardBlock(5, 8).setColour((byte)0);
        this.game.data().getBoardBlock(6, 8).setColour((byte)15);
        this.game.data().getBoardBlock(7, 8).setColour((byte)15);
        this.game.data().getBoardBlock(5, 7).setColour((byte)0);
        this.game.data().getBoardBlock(6, 7).setColour((byte)0);
        this.game.data().getBoardBlock(7, 7).setColour((byte)0);
      }
      else if (this.totalticks >= 0L)
      {
        this.game.data().getBoardBlock(5, 11).setColour((byte)0);
        this.game.data().getBoardBlock(6, 11).setColour((byte)0);
        this.game.data().getBoardBlock(7, 11).setColour((byte)0);
        this.game.data().getBoardBlock(5, 10).setColour((byte)15);
        this.game.data().getBoardBlock(6, 10).setColour((byte)15);
        this.game.data().getBoardBlock(7, 10).setColour((byte)0);
        this.game.data().getBoardBlock(5, 9).setColour((byte)15);
        this.game.data().getBoardBlock(6, 9).setColour((byte)0);
        this.game.data().getBoardBlock(7, 9).setColour((byte)0);
        this.game.data().getBoardBlock(5, 8).setColour((byte)15);
        this.game.data().getBoardBlock(6, 8).setColour((byte)15);
        this.game.data().getBoardBlock(7, 8).setColour((byte)0);
        this.game.data().getBoardBlock(5, 7).setColour((byte)0);
        this.game.data().getBoardBlock(6, 7).setColour((byte)0);
        this.game.data().getBoardBlock(7, 7).setColour((byte)0);
      }
    }
    if ((this.ticks >= 20) || (this.game.data().isMovingInstantlyDown()))
    {
      if (!this.game.data().isGameOver())
      {
        this.game.data().moveSelectedDown();
      }
      else
      {
        if (this.gameOverCounterLine > 20) {
          return;
        }
        if (!Settings.redScreenOfDeath)
        {
          this.gameOverCounterLine = 17;
          this.timeoutTicks = (Settings.gameOverTimeout * 10);
          return;
        }
        int x = 1;
        while (x <= 11)
        {
          this.game.data().getBoardBlock(x, this.gameOverCounterLine).setRed();
          x++;
        }
        this.gameOverCounterLine += 1;
        if (this.gameOverCounterLine > 20) {
          this.timeoutTicks = (Settings.gameOverTimeout * 10);
        }
        this.ticks = 14;
        return;
      }
      this.ticks = 17;
      if (this.totalticks <= 24000L) {
        this.ticks = 15;
      }
      if (this.totalticks <= 12000L) {
        this.ticks = 12;
      }
      if (this.totalticks <= 9000L) {
        this.ticks = 10;
      }
      if (this.totalticks <= 7200L) {
      }
      if (this.totalticks <= 6000L) {
        this.ticks = 6;
      }
      if (this.totalticks <= 4800L) {
        this.ticks = 5;
      }
      if (this.totalticks <= 4000L) {
        this.ticks = 3;
      }
      if (this.totalticks <= 3000L) {
        this.ticks = 2;
      }
      if (this.totalticks <= 1800L) {
        this.ticks = 1;
      }
      if (this.totalticks <= 600L) {
        this.ticks = 0;
      }
      this.gameSpeed = (5 - this.ticks / 6);
    }
    
  
    
  }
  
  public void disableMusic()
  {
    this.isMusicEnabled = (!this.isMusicEnabled);
    p.setPlaying(this.isMusicEnabled);
  }
 
}
