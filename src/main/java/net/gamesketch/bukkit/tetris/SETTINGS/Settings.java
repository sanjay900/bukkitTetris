package net.gamesketch.bukkit.tetris.SETTINGS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.sanjay900.nmsUtil.util.Button;

import net.gamesketch.bukkit.tetris.GAME.Game;

public class Settings
{
	public static boolean canUseTetrisEverywhere = false;
	public static boolean serverWide = false;
	public static boolean canDestroyField = false;
	public static boolean canMoveOutField = false;
	public static boolean canSpectate = true;
	public static boolean useAdminLocations = false;
	public static boolean LightUpArena = true;
	public static int gameOverTimeout = 60;
	public static int mustStandOnBlockTypeId = 159;
	public static boolean countdownOnStartup = true;
	public static boolean redScreenOfDeath = true;
	public static boolean isPlayingMusic = true;
	public static boolean AnouncesHighscore = true;
	public static boolean UniqueHighscore = true;

	static File file = new File("plugins/Tetris/settings.txt");
	static File folder = new File("plugins/Tetris/");
	private static int button;

	public static boolean Load()
	{
		if (!checkFile()) {
			return false;
		}
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s;
			while ((s = in.readLine()) != null)
			{
				if (s.startsWith("canusetetriseverywhere=")) {
					canUseTetrisEverywhere = getBoolean(s.split("=")[1], false);
				}
				if (s.startsWith("candestroyfield=")) {
					canDestroyField = getBoolean(s.split("=")[1], false);
				}
				if (s.startsWith("canmoveoutfield=")) {
					canMoveOutField = getBoolean(s.split("=")[1], false);
				}
				if (s.startsWith("gameovertimeout=")) {
					gameOverTimeout = getInt(s.split("=")[1], 60);
				}
				if (s.startsWith("muststandonblocktype=")) {
					mustStandOnBlockTypeId = getInt(s.split("=")[1], 41);
				}
				if (s.startsWith("countdownonstart=")) {
					countdownOnStartup = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("redscreenofdeath=")) {
					redScreenOfDeath = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("anouncehighscore=")) {
					AnouncesHighscore = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("uniquehighscore=")) {
					UniqueHighscore = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("playmusic=")) {
					isPlayingMusic = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("nopermissions=")) {
					serverWide = getBoolean(s.split("=")[1], false);
				}
				if (s.startsWith("spectate=")) {
					canSpectate = getBoolean(s.split("=")[1], true);
				}
				if (s.startsWith("useadminlocation=")) {
					useAdminLocations = getBoolean(s.split("=")[1], false);
				}

			}
			in.close();
			if (gameOverTimeout <= 0) {
				gameOverTimeout = 60;
			}
			if ((mustStandOnBlockTypeId >= 255) || (mustStandOnBlockTypeId < 0)) {
				mustStandOnBlockTypeId = 41;
			}
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean Save()
	{
		if (!checkFile()) {
			return false;
		}
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			write(out, "##########Global settings##########");
			write(out, "##### True if you want to be able to break blocks in the field #####");
			write(out, "CanDestroyField=" + canDestroyField);
			write(out, " ");
			write(out, "##### True if you want to be able to walk out of the field #####");
			write(out, "CanMoveOutField=" + canMoveOutField);
			write(out, " ");
			write(out, "##### amount of seconds after gameover until the field automatically goes away #####");
			write(out, "GameOverTimeOut=" + gameOverTimeout);
			write(out, " ");
			write(out, "##### True if you want to anounce new highscores etc");
			write(out, "AnounceHighscore=" + AnouncesHighscore);
			write(out, " ");
			write(out, "##### True if you want a user to appear in the highscores at most one time");
			write(out, "UniqueHighscore=" + UniqueHighscore);
			write(out, " ");
			write(out, "##### True if you want the tetris theme(tm) to be played #####");
			write(out, "PlayMusic=" + isPlayingMusic);
			write(out, " ");
			write(out, "##### False if you want permissions/ops only, True for anyone");
			write(out, "NoPermissions=" + serverWide);
			write(out, " ");
			write(out, "##### True if you want to be able to /tspec <player> #####");
			write(out, "Spectate=" + canSpectate);
			write(out, " ");
			write(out, " ");
			write(out, "##########Graphical settings##########");
			write(out, "##### True if you want an start countdown on start #####");
			write(out, "CountDownOnStart=" + countdownOnStartup);
			write(out, " ");
			write(out, "##### True if you want the tetris screen to go red when gameover #####");
			write(out, "RedScreenOfDeath=" + redScreenOfDeath);
			write(out, " ");
			write(out, "##### True if you want the arena lit if its too dark #####");
			write(out, "LightUpArena=" + LightUpArena);
			write(out, " ");
			write(out, "###########Location settings###########");
			write(out, "##### True if you want to be able to /tetris on any location #####");
			write(out, "CanUseTetrisEverywhere=" + canUseTetrisEverywhere);
			write(out, " ");
			write(out, "##### The block type-id to stand on if you have 'canusetetriseverywhere' to false #####");
			write(out, "##### It will check if the player using /tetris stands on the block with that type");
			write(out, "MustStandOnBlockType=" + mustStandOnBlockTypeId);
			write(out, " ");
			write(out, "##### True if you want admin-defined places only");
			write(out, "UseAdminLocation=" + useAdminLocations);
			write(out, " ");
			write(out, "JUST AN NOTE ABOUT THE ORDERING OF THE LOCATION SETTINGS");
			write(out, "- IT WILL FIRST CHECK IF TETRIS CAN BE USED EVERYWHERE, IF SO: START");
			write(out, "- THEN IT WILL CHECK IF IT USES ADMIN-ONLY LOCATIONS, IF SO: START");
			write(out, "- AND THEN IT WILL CHECK IF HE STANDS ON THE BLOCK DEFINED");
			write(out, " ");
			write(out, " ");

			out.close();
			return true;
		}
		catch (IOException localIOException) {}
		return false;
	}

	private static void write(BufferedWriter out, String s)
			throws IOException
	{
		out.write(s);
		out.newLine();
	}

	private static boolean checkFile()
	{
		if (!file.exists()) {
			try
			{
				folder.mkdirs();
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private static boolean getBoolean(String text, boolean def)
	{
		if (def) {
			return (!text.contains("false")) && (!text.contains("no"));
		}
		return (text.contains("true")) || (text.contains("yes"));
	}

	private static int getInt(String text, int def)
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch (NumberFormatException localNumberFormatException) {}
		return def;
	}

	public static boolean performButtonAction(Game game, List<Button> buttons) {
		if (game == null) {
			return false;
		}
		if (game.data() == null) {
			return false;
		}
		if (buttons.contains(Button.LEFT) && !game.data().isMoving())
		{
			button = 0;
			game.data().moveSelectedLeft();return true;
		}
		else if (buttons.contains(Button.RIGHT) && !game.data().isMoving())
		{
			button = 0;
			game.data().moveSelectedRight();return true;
		}
		else if (buttons.contains(Button.DOWN) && !game.data().isMoving())
		{
			button = 0;
			game.data().moveSelectedDown();return true;
		}
		else if (buttons.contains(Button.JUMP) && !game.data().isMovingInstantlyDown())
		{
			game.data().moveInstantlyDown();return true;
		}
		else 
			if (buttons.contains(Button.UNMOUNT))
			{
				game.data().rotateSelected();return true;
			}
			else {
				button = button + 1;
				if (button > 20) {
					game.data().setMoving(false);
				}
				return true;
			}
	}
	public static int buttonLeft = 1;
	public static int buttonRight = 2;
	public static int buttonUp = 4;
	public static int buttonDown = 3;
	public static int buttonScrollUp = 0;
	public static int buttonScrollDown = 0;
	public static int buttonSneakScrollUp = 0;
	public static int buttonSneakScrollDown = 0;
	public static int buttonLeftAir = 5;
	public static int buttonRightAir = 5;
}
