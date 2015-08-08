package com.sanjay900.tetris.SETTINGS;

import java.util.List;

import com.sanjay900.nmsUtil.util.Button;
import com.sanjay900.nmsUtil.util.Cooldown;
import com.sanjay900.tetris.GAME.Game;

public class Settings
{

	private static int button;
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
		else if (buttons.contains(Button.UNMOUNT) && !game.data().isMovingInstantlyDown())
		{
			game.data().moveInstantlyDown();return true;
		}
		else 
			if (buttons.contains(Button.JUMP) && Cooldown.tryCooldown(game.getPlayer(), "rotate", 750l))
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
